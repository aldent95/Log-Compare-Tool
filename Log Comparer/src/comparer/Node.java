package comparer;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private ArrayList<Node> children = new ArrayList<Node>();
	private HashMap<String, Integer> values= new HashMap<String, Integer>();
	private final String key;
	public Node(String key){
		this.key = key;
	}
	public String getKey(){
		return key;
	} 
	/**
	 * @return the children
	 */
	public ArrayList<Node> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	public void addChild(Node child){
		if(child != null){
		this.children.add(child);
		}
	}
	public Node getChild(String key){
		for(Node child: children){
			if(child.getKey().equals(key)) return child;
		}
		return null;
	}
	/**
	 * @return the values
	 */
	public HashMap<String, Integer> getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(HashMap<String, Integer> values) {
		this.values = values;
	}
	public void addValues(String value){
		if(value == null) return;
		if(values.containsKey(value)){
			values.put(value.trim(), values.get(value)+1);
		}
		else{
			values.put(value, 1);
		}
	}
	public void print() {
	if(!values.equals("")){
		System.out.println(key + "\n" + values + "\n\n");
	}
	if(children != null){
		for(Node child: children){
			child.print();
		}
	}
		
	}
	public boolean hasChild(String string) {
		for(Node child: children){
			if(child.getKey().equals(string)) return true;
		}
		return false;
	}
}
