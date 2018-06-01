package inti.recon.backend;

import org.opencv.core.Point;

public class Quadrilateral {
	Point a;
	Point b;
	Point c;
	Point d;

	public Quadrilateral(Point a, Point b, Point c, Point d) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public boolean isConvex() {
		double angulo_A = angulo(a, d, b);
		double angulo_B = angulo(b, a, c);
		double angulo_C = angulo(c, b, d);
		double angulo_D = angulo(d, c, a);

		return (angulo_A < (Math.PI - 0.35)) && (angulo_A > 0.35)
				&& (angulo_B < (Math.PI - 0.35)) && (angulo_B > 0.35)
				&& (angulo_C < (Math.PI - 0.35)) && (angulo_C > 0.35)
				&& (angulo_D < (Math.PI - 0.35)) && (angulo_D > 0.35);
	}

	// TODO Para quŽ queremos esto?
	public boolean isLargeEnough() {
		double distancia_AB = distancia(a, b);
		double distancia_AC = distancia(a, c);
		double distancia_AD = distancia(a, d);

		return (distancia_AB > 100 && distancia_AC > 120 && distancia_AD > 50);
	}

	public Point getA() {
		return a;
	}

	public Point getB() {
		return b;
	}

	public Point getC() {
		return c;
	}

	public Point getD() {
		return d;
	}

	// TODO Use norm function in OpenCV?
	private double distancia(Point pt1, Point pt2) {
		double res;
		res = Math
				.sqrt(Math.pow(pt1.x - pt2.x, 2) + Math.pow(pt1.y - pt2.y, 2));
		return res;
	}

	private double angulo(Point uno, Point dos, Point tres) {

		// transladamos al origen de coordenadas los tres puntos
		Point pi = new Point(dos.x - uno.x, dos.y - uno.y);
		Point pj = new Point(tres.x - uno.x, tres.y - uno.y);
		// calculamos su angulo de coordenada polar
		double ang_pi = Math.atan2((double) pi.x, (double) pi.y);
		double ang_pj = Math.atan2((double) pj.x, (double) pj.y);

		// hallamos la diferencia
		double ang = ang_pj - ang_pi;

		// Si el angulo es negativo le sumamos 2PI para obtener el
		// angulo en el intervalo [0-2PI];
		// siempre obtenemos Ã¡ngulos positivos (en sentido antihorario)
		if (ang < 0.0)
			return ang + (2.0 * Math.PI);
		else
			return ang;
	}

}
