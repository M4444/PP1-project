package rs.ac.bg.etf.pp1.sm120554d.ext;

import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.SymbolTableVisitor;

public class MySymbolTableVisitor extends SymbolTableVisitor {

	protected StringBuilder output = new StringBuilder();
	protected final String indent = "   ";
	protected StringBuilder currentIndent = new StringBuilder();
	
	private boolean inProgram = false;
	private boolean inClass = false;
	private boolean inFunction = false;
	
	protected void nextIndentationLevel() {
		currentIndent.append(indent);
	}
	
	protected void previousIndentationLevel() {
		if (currentIndent.length() > 0)
			currentIndent.setLength(currentIndent.length()-indent.length());
	}
	
	@Override
	public void visitObjNode(Obj objToVisit) {
		//output.append("[");
		switch (objToVisit.getKind()) {
		case Obj.Con:  output.append("Con "); break;
		case Obj.Var:  output.append("Var "); break;
		case Obj.Type: output.append("Type "); break;
		case Obj.Meth: output.append("Meth ");
					   if(inProgram && !inClass) inFunction = true; 
					   break;
		case Obj.Fld:  output.append("Fld "); break;
		case Obj.Prog: output.append("Prog "); inProgram = true; break;
		}
		
		output.append(objToVisit.getName());
		output.append(": ");
		
		//if ((Obj.Var == objToVisit.getKind()) && "this".equalsIgnoreCase(objToVisit.getName()))
		//	output.append("");
		//else
		if(inProgram) objToVisit.getType().accept(this);
		
		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel() + " ");
				
		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
			output.append("\n");
			nextIndentationLevel();
		}
		

		for (Obj o : objToVisit.getLocalSymbols()) {
			output.append(currentIndent.toString());
			o.accept(this);
			output.append("\n");
		}
		
		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) 
			previousIndentationLevel();

		//output.append("]");
		
	}

	@Override
	public void visitScopeNode(Scope scope) {
		for (Obj o : scope.values()) {
			o.accept(this);
			output.append("\n");
		}
	}


	@Override
	public void visitStructNode(Struct structToVisit) {
		switch (structToVisit.getKind()) {
		case Struct.None:
			output.append("notype");
			break;
		case Struct.Int:
			output.append("int");
			break;
		case Struct.Char:
			output.append("char");
			break;
		case Struct.Array:
			output.append("Arr of ");
			
			switch (structToVisit.getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Class:
				output.append("Class");
				break;
			}
			break;
		case Struct.Class:
			if(!inClass && !inFunction)
			{
				inClass = true;
				output.append("Class [");
				for (Obj obj : structToVisit.getMembers()) {
					obj.accept(this);
				}
				output.append("]");
				inClass = false;
			}
			else 
			{
				String className = MyTab.getClasNameFromType(structToVisit);
				
				if(className != null)
					output.append(className);
				else
					output.append("Class");
			}
			break;
		}

	}

	public String getOutput() {
		return output.toString();
	}

}
