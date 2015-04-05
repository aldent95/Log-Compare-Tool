package comparer;

public class Result implements Comparable<Object> {
	private String field;
	private String value;
	private int count;
	public Result(String f, String v, int c){
		setCount(c);
		setValue(v);
		setField(f);
	}
	public boolean equalsString(String f, String v){
		if(field.equals(f) && value.equals(v)){
			count++;
			return true;
		}
		return false;
	}
	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int compareTo(Object arg0) {
		if(arg0 instanceof Result){
			if(((Result) arg0).getCount() > count) return 1;
			else if(((Result) arg0).getCount() < count) return -1;
		}
		return 0;
	}
	@Override
	public String toString() {
		return field + ",\t value=" + value + ",\t count="
				+ count + "\n";
	}
}
