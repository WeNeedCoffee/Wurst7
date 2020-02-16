package net.wurstclient.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.text.Text;

//Lpath/to/class; -> Class, I -> int, J -> long, Z -> boolean, V -> void, F -> float, D -> double, C -> char, S -> short, B -> byte, I believe
@Mixin(BookEditScreen.class)
public class BookEditScreenMixin extends Screen {
	@Shadow
	private String title;

	@Shadow
	private boolean dirty;
	@Shadow
	private boolean signing;
	@Shadow
	public void updateButtons() {
		
	}
	protected BookEditScreenMixin(Text title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Inject(at = { @At("HEAD") }, method = { "stripFromatting(Ljava/lang/String;)V" }, cancellable = true)
	public void stripFromatting(String s, CallbackInfoReturnable<String> cir) {
		cir.setReturnValue(s);
	}

	@Shadow 
	public void writeString(String s) {
		
	}
	@Inject(at = { @At("HEAD") }, method = { "charTyped(CI)Z" }, cancellable = true)
	public void charTyped(char chr, int keyCode, CallbackInfoReturnable<Boolean> cir) {
		if (super.charTyped(chr, keyCode)) {
			cir.setReturnValue(true);
		} else if (this.signing) {
			if (this.title.length() < 16) {
				this.title = this.title + Character.toString(chr);
				this.updateButtons();
				this.dirty = true;
				cir.setReturnValue(true);
			} else {
				cir.setReturnValue(false);
			}
		} else {
			this.writeString(Character.toString(chr == "$".charAt(0) ? "\u00a7".charAt(0) : chr));
			cir.setReturnValue(true);
		}
	}
}
