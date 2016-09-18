package main;

/**
 * class Lesson
 * 
 * Represents an entry in the timetable and holds
 * all necessary information
 * 
 * @author d.lentz
 *
 */
public class Lesson {
	private String teacher;
	private String room;
	private String course;
	private boolean color;

	public Lesson() {
		this.teacher = "";
		this.room = "";
		this.course = "";
		this.color = false;
	}
	
	public Lesson(boolean noResultsetFound) {
		this.teacher = "";
		this.room = "";
		this.course = "";
		this.color = false;
	}
	
	public Lesson(String i_teacher, String i_room, String i_subject) {
		this.teacher = i_teacher;
		this.room = i_room;
		this.course = i_subject;
		this.color = true;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getCourse() {
		return course;
	}

	public void setSubject(String course) {
		this.course = course;
	}
	
	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}
	
}
