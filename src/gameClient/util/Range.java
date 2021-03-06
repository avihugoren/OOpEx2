package gameClient.util;
/**
 * This class represents a simple 1D range of shape [min,max]
 * @author boaz_benmoshe
 *
 */
public class Range {
	private double _min, _max;

	/**
	 * constructor
	 * @param min,max
	 */
	public Range(double min, double max) {
		set_min(min);
		set_max(max);
	}

	/**
	 * copy constructor
	 * @param x
	 */
	public Range(Range x) {
		this(x._min, x._max);
	}

	public String toString() {
		String ans = "["+this.get_min()+","+this.get_max()+"]";
		if(this.isEmpty()) {ans = "Empty Range";}
		return ans;
	}

	/**
	 * return false if the range is not correct
	 * @return
	 */
	public boolean isEmpty() {
		return this.get_min()>this.get_max();
	}

	/**
	 * get the max value of this range
	 * @return
	 */
	public double get_max() {
		return _max;
	}

	/**
	 * get the range length
	 * @return
	 */
	public double get_length() {
		return _max-_min;
	}

	/**
	 * set the max value of the range
	 * @param _max
	 */
	private void set_max(double _max) {
		this._max = _max;
	}

	/**
	 * get the min value of the range
	 * @return
	 */
	public double get_min() {
		return _min;
	}

	/**
	 * set the min value of the range
	 * @param _min
	 */
	private void set_min(double _min) {
		this._min = _min;
	}

	/**
	 * Compute d while referring to the axis of min and max
	 * @param d
	 * @return
	 */
	public double getPortion(double d) {
		double d1 = d-_min;
		double ans = d1/get_length();
		return ans;
	}

	/**
	 * Compute the original d before the getPortion
	 * @param p
	 * @return
	 */
	public double fromPortion(double p) {
		return _min+p*get_length();
	}
}
