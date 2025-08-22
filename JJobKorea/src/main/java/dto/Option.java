// dto/Option.java
package dto;

public class Option {
	private int id;
	private String name;

	public Option(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() { // ← JSP EL이 찾는 메서드
		return id;
	}

	public String getName() { // ← JSP EL이 찾는 메서드
		return name;
	}
}