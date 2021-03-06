package rs.ac.bg.etf.pp1.sm120554d.ext;

import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;

public class MyTab extends Tab {
	public static Scope programScope; // tekuci opseg
	private static boolean inProgramScope = false;
	
	public static final Struct boolType = new Struct(Struct.Bool);
	public static final Struct stringType = new Struct(Struct.Array, charType);
	
	public static void init() {
		Tab.init();
		
		currentScope.addToLocals(new Obj(Obj.Type, "bool", boolType));
		currentScope.addToLocals(new Obj(Obj.Type, "string", stringType));
	}
	
	public static void openScope() {
		Tab.openScope();
		
		if(!inProgramScope)
		{
			programScope = Tab.currentScope();
			inProgramScope = true;
		}
	}
	
	public static Obj getClasFromType(Struct type)
	{
		for (Scope s = programScope; s != null; s = s.getOuter()) {
			for(Obj obj : s.values())
			{
				if(obj.getType().equals(type))
				{
					return obj;
				}
			}
		}
		return null;
	}
	
	public static String getClasNameFromType(Struct type)
	{
		Obj classObj = getClasFromType(type);
		
		if(classObj == null)
			return null;
		else
			return classObj.getName();
	}
}
