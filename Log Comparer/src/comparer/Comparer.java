package comparer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Comparer {
	HashMap<String, ArrayList<Result>> dxDiagResults = new HashMap<String, ArrayList<Result>>();
	private BufferedReader data;
	private Node root = null;
	private String line = "";

	public Comparer() {
	}

	public void ensureClose() throws IOException {
		if (data != null)
			data.close();

	}

	public ArrayList<Result> getResult(String type, String toLoad) {
		if (type.equals("dxDiag")) {
			return dxDiagResults.get(toLoad);
		}
		return null; // Should never happen
	}

	public Set<String> loadDx(int runs) throws IOException {
		dxDiagResults = new HashMap<String, ArrayList<Result>>();
		String currentSection = "";
		ArrayList<Result> tempResults = new ArrayList<Result>();
		for (int i = 1; i <= runs; i++) {
			File in;
			if (i == 1) {
				in = new File("./dxDiags/dxdiag.txt");
			} else
				in = new File("./dxDiags/dxdiag (" + i + ").txt");
				data = new BufferedReader(new FileReader(in));
				line = "";
				while (true) {
					line = data.readLine();
					if (line == null)
						break;
					if (line.startsWith("+"))
						continue;
					if (line.startsWith("---")) {
						if (!currentSection.equals("")) {
							dxDiagResults.put(currentSection, tempResults);
							tempResults = new ArrayList<Result>();
						}
						String next = data.readLine();
						currentSection = next;
						if (dxDiagResults.containsKey(currentSection))
							tempResults = dxDiagResults.get(currentSection);
						String temp = data.readLine();
						if (!temp.startsWith("----"))
							data.readLine();
						continue;
					}

					String[] input = line.split(":");
					String value = "";
					boolean deinterlace = false;
					String field = input[0].trim();
					while (true) {
						if (deinterlace) {
							line = data.readLine();
							input = line.split(":");
						}
						if (!(line.trim().startsWith("{")))
							deinterlace = false;
						if (deinterlace) {
							for (int j = 0; j < input.length; j++) {
								value += input[j];
								if (j > 1)
									value += ":";
							}
						} else {
							for (int j = 1; j < input.length; j++) {
								value += input[j];
								if (j > 1)
									value += ":";
							}
						}
						if (input[0].trim().equals("Deinterlace Caps")) {
							deinterlace = true;
						}
						if (!deinterlace)
							break;
					}
					if (tempResults.size() == 0) {
						tempResults.add(new Result(field, value, 1));
					} else {
						boolean found = false;
						for (Result r : tempResults) {
							if (r.equalsString(field, value)) {
								found = true;
								break;
							}
						}
						if (!found) {
							tempResults.add(new Result(field, value, 1));
						}
					}
				}
		}
			data.close();
		return dxDiagResults.keySet();
	}

	public Node loadSettings(int runs) throws IOException {
		line = "";
		int depth = 0;
		System.out.println("Starting tree build");
		for (int i = 1; i <= runs; i++) {
			System.out.println("Loading file " + i);
			File in;
			if (i == 1) {
				in = new File("./settingsLogs/settings.yaml");
				data = new BufferedReader(new FileReader(in));
			} else{
				in = new File("./settingsLogs/settings (" + i + ").yaml");
				data = new BufferedReader(new FileReader(in));
				line = data.readLine();
			}
			
			while (true) {
				if (root == null) {
					root = new Node("Settings");
					line = data.readLine();
					continue;
				}
				if (line == null)
					break;
				if (line.matches("^ {" + (depth) * 2
						+ "}([A-Z]|[a-z]|_)+:{1}.*")) {
					if(line.startsWith("public:")){
						System.out.println("Public found");
						root.addChild(loadSettingsPub(depth, root));
					}else{
					root.addChild(loadSettingsRec(depth,root));
					}
				}
			}
		}
		System.gc();
		return root;
	}

	private Node loadSettingsRec(int depth, Node parent){
		try {
			line = line.trim();
			String tabs = "";
			for(int i = 1; i <=depth; i++){
				tabs += "  ";
			}
			String[] values = line.split(":");
			//System.out.println("Tree building depth: " + depth + " + key: " + values[0]);
			Node current;
			if(parent.hasChild(tabs+values[0])){
				current = parent.getChild(tabs+values[0]);
			}else current = new Node(tabs+values[0]);
			for(int i = 1; i < values.length; i++){
				if(!values[i].equals("")){
					current.addValues(tabs+tabs+values[i]);
				}
			}
			line = data.readLine();
			while(true){
				if(line == null){
					if(parent.hasChild(current.getKey())){
						 return null;
					 }
					return current;
				}
				for(int i = 1; i <=depth; i++){
					 if( line.matches("^ {"+(depth-i)*2+"}([A-Z]|[a-z]|_)+:{1}.*")){
						 if(parent.hasChild(current.getKey())){
							 return null;
						 }
						 return current;
					 }
				}
				if(line.matches("^ {"+(depth+1)*2+"}([A-Z]|[a-z]|_)+:{1}.*")){
					current.addChild(loadSettingsRec(depth+1, current));
				}else if(line.matches("^ {"+(depth)*2+"}([A-Z]|[a-z]|_)+:{1}.*")){
					if(parent.hasChild(current.getKey())){
						 return null;
					 }
					return current;
				}else{
					current.addValues(line.trim());
					line = data.readLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private Node loadSettingsPub(int depth, Node parent){
		try {
			line = line.trim();
			String tabs = "";
			for(int i = 1; i <=depth; i++){
				tabs += "  ";
			}
			String[] values = line.split(":");
			//System.out.println("Tree building pub depth: " + depth + " + key: " + values[0]);
			Node current;
			if(parent.hasChild(tabs+values[0])){
				current = parent.getChild(tabs+values[0]);
			}else current = new Node(tabs+values[0]);
			for(int i = 1; i < values.length; i++){
				if(!values[i].equals("")){
					current.addValues(tabs+tabs+values[i]);
				}
			}
			line = data.readLine();
			line = line.replace("!!python/unicode ", "");
			line = line.replace("'", "");
			line = line.replace("'", "");
			//System.out.println(line);
			while(true){
				if(line == null){
					if(parent.hasChild(current.getKey())){
						 return null;
					 }
					return current;
				}
				for(int i = 1; i <=depth; i++){
					
					 if(line.matches("^ {"+((depth-i)*2)+"}([A-Z]|[a-z]|_)+:{1}.*")){
						 if(parent.hasChild(current.getKey())){
							 return null;
						 }
						 return current;
					 }
				}
				if(line.matches("^ {"+((depth+1)*2)+"}([A-Z]|[a-z]|_)+:{1}.*")){
					current.addChild(loadSettingsPub(depth+1, current));
				}else if(line.matches("^ {"+((depth)*2)+"}([A-Z]|[a-z]|_)+:{1}.*")){
					if(parent.hasChild(current.getKey())){
						 return null;
					 }
					return current;
				}else{
					current.addValues(line.trim());
					line = data.readLine();
					line = line.replace("!!python/unicode ", "");
					line = line.replace("'", "");
					line = line.replace("'", "");
					//System.out.println(line);
				} 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<String> loadGeneric(int i) {
		// TODO create generic file load method
		return null;
	}
}
