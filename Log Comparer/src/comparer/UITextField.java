package comparer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JTextArea;

public class UITextField extends JTextArea {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7615699111912843771L;

		public void display(ArrayList<Result> result, boolean excludeEmptyValues, boolean excludeHigherCounts, int runs) {
			this.setText("");
			Collections.sort(result);
			for(Result r: result){
				if(!((excludeHigherCounts && r.getCount() > runs) || (excludeEmptyValues && r.getValue().equals("")))){
					this.append(r.toString() + "\n");
				}
			}
			
		}
		public void display(Node node, boolean excludeEmptyValues, boolean excludeHigherCounts, int runs) {
			HashMap<String,Integer> result = node.getValues();
			this.setText("");
			ArrayList<String> temp = new ArrayList<String>();
			for(String r: result.keySet()){
				temp.add(r);
			}
			Collections.sort(temp);
			this.append(node.getKey() + "\n");
			for(String s: temp){
				if(!((excludeHigherCounts && result.get(s) > runs) || (excludeEmptyValues && s.equals("")))){
					this.append(s + "\t count: " + result.get(s) + "\n");
				}
			}
			
		}


}
