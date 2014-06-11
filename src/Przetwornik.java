import Jama.Matrix;
import Jama.SingularValueDecomposition;


public class Przetwornik {

	Matrix P1, P2;

	/**
	 * Kalibruje przetwornik generując macierze rzutowania
	 * @param x1 punkty na pierwszym obrazie 2D
	 * @param x2 punkty na drugim obrazie 2D
	 * @param X punkty w przestrzeni 3D
	 */
	public void skalibruj(Matrix x1, Matrix x2, Matrix X) {
		P1 = kalibr(x1, X);
		P2 = kalibr(x2, X);
		
		x1.print(6, 4);
		x2.print(6, 4);
		X.print(6, 4);

		P1.print(6, 4);
		P2.print(6, 4);
	}
	
	/**
	 * Funkcja kalibrująca, generuje macierz rzutowania
	 * @param x punkty na obrazie 2D
	 * @param X punkty w przestrzeni 3D
	 * @return macierz rzutowania
	 */
	private Matrix kalibr(Matrix x, Matrix X) {
		
		int n = Math.min(x.getRowDimension(), X.getRowDimension());
		
		Matrix M = new Matrix(n*2, 12); // 0 x 12 wypełniona zerami
		Matrix transpX = X.transpose();		
		
		for (int i = 0; i < n; ++i) {
			Matrix XT = new Matrix(4,1,1.0); // wypełniona jedynkami			
			XT.setMatrix(0, 2, 0, 0, transpX.getMatrix(0, 2, i, i));
			
			double xx = x.transpose().get(0, i);
			double yy = x.transpose().get(1, i);
			
			Matrix xXT = XT.times(xx).uminus();
			Matrix yXT = XT.times(yy).uminus();
			
			M.setMatrix(2*i, 2*i, 0, 3, XT.transpose());
			M.setMatrix(2*i+1, 2*i+1, 4, 7, XT.transpose());
			M.setMatrix(2*i, 2*i, 8, 11, xXT.transpose());
			M.setMatrix(2*i+1, 2*i+1, 8, 11, yXT.transpose());
		}
		
		SingularValueDecomposition svd = M.svd();
		
		Matrix R = svd.getV().getMatrix(0, 11, 11, 11).transpose();
		Matrix P, P1, P2, P3;
		
		P1 = R.getMatrix(0, 0, 0, 3);
		P2 = R.getMatrix(0, 0, 4, 7);
		P3 = R.getMatrix(0, 0, 8, 11);
		
		P = new Matrix(3, 4);
		P.setMatrix(0, 0, 0, 3, P1);
		P.setMatrix(1, 1, 0, 3, P2);
		P.setMatrix(2, 2, 0, 3, P3);
		
		return P;
	}

	
	/**
	 * Znajduje współrzędne punktu w przestrzeni 3D
	 * @param xt1 współrzędne na pierwszym obrazie 2D
	 * @param xt2 współrzędne na drugim obrazie 2D
	 * @return współrzędne punktu w przestrzeni 3D
	 */
	public Matrix rekonstruuj(Matrix xt1, Matrix xt2) {
		
		Matrix XYZ = new Matrix(3,0);
		
		double x1 = xt1.get(0, 0);
		double y1 = xt1.get(1, 0);
		
		double[][] tempL1 = {
				{P1.get(0,0) - x1*P1.get(2,0), P1.get(0,1) - x1*P1.get(2,1), P1.get(0,2) - x1*P1.get(2,2)},
				{P1.get(1,0) - y1*P1.get(2,0), P1.get(1,1) - y1*P1.get(2,1), P1.get(1,2) - y1*P1.get(2,2)}
		};
		
		Matrix L1 = new Matrix(tempL1);
		
		double[][] tempB1 = {
				{x1*P1.get(2,3) - P1.get(0, 3)},
				{y1*P1.get(2,3) - P1.get(1, 3)}
		};
		
		Matrix B1 = new Matrix(tempB1);

		double x2 = xt2.get(0, 0);
		double y2 = xt2.get(1, 0);
		
		double[][] tempL2 = {
				{P2.get(0,0) - x2*P2.get(2,0), P2.get(0,1) - x2*P2.get(2,1), P2.get(0,2) - x2*P2.get(2,2)},
				{P2.get(1,0) - y2*P2.get(2,0), P2.get(1,1) - y2*P2.get(2,1), P2.get(1,2) - y2*P2.get(2,2)}
		};
		
		Matrix L2 = new Matrix(tempL2);
		
		double[][] tempB2 = {
				{x2*P2.get(2,3) - P2.get(0, 3)},
				{y2*P2.get(2,3) - P2.get(1, 3)}
		};
		
		Matrix B2 = new Matrix(tempB2);

		Matrix L = new Matrix(4,3);
		Matrix B = new Matrix(4,1);
		
		L.setMatrix(0, 1, 0, 2, L1);
		L.setMatrix(2, 3, 0, 2, L2);
		
				
		B.setMatrix(0, 1, 0, 0, B1);
		B.setMatrix(2, 3, 0, 0, B2);
		
		XYZ = L.inverse().times(B);
				
		return XYZ;
	}
}
