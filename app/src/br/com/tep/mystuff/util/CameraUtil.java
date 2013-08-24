package br.com.tep.mystuff.util;



import android.graphics.Bitmap;
import android.graphics.Matrix;

public class CameraUtil {

	public static Bitmap rotateImage(final int degree, Bitmap photo) {
		// Rotaciona a imagem em 90 graus
		Matrix matrix = new Matrix();
		matrix.setRotate(degree);

		return Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);
	}

}
