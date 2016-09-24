package main;

import java.util.ArrayList;

/**
 * class Entry
 * 
 * Represents an entry in the timetable and holds
 * all necessary information
 * 
 * @author f.petruschke
 *
 */
public class Entry {
	private String weekdayShortname;
	private String timeslotFrom;
	private ArrayList<String> teacher;
	private String room;
	private String course;
	private boolean color;

	/**
	 * Entry constructor
	 */
	public Entry() {
		this.weekdayShortname = "";
		this.timeslotFrom = "";
		this.teacher = new ArrayList<>();
		this.room = "";
		this.course = "";
		this.color = false;
	}
	
	/**
	 * Entry constructor
	 * 
	 * For empty entries
	 * 
	 * @param noResultsetFound	boolean		if set empty entry is created
	 */
	public Entry(boolean noResultsetFound) {
		this.weekdayShortname = "";
		this.timeslotFrom = "";
		this.teacher = new ArrayList<>();
		this.room = "";
		this.course = "";
		this.color = false;
	}
	
	/**
	 * Entry constructor
	 * 
	 * Constructor for timeranges of the courses (begin - end)
	 * 
	 * @param from		String		course begin - e.g. "7:45"
	 * @param until		String		course end - e.g: "8:30"
	 */
	public Entry(String from, String until) {
		this.weekdayShortname = "";
		this.timeslotFrom = "";
		
		ArrayList<String> teacherList = new ArrayList<>();
		teacherList.add(from);
		this.teacher = teacherList;
		
		this.room = " - ";
		this.course = until;
		this.color = false;
	}
	
	/**
	 * Entry constructor
	 * 
	 * @param i_weekdayShortname	String		german shortname of the weekday - e.g. "Mo"
	 * @param i_timeslotFrom		String		course begin time as String - e.g. "7:45"
	 * @param i_teacher				ArrayList	ArrayList containing teachernames - e.g. "Wm"
	 * @param i_room				String		name of the room
	 * @param i_course				String		name of the course
	 */
	public Entry(String i_weekdayShortname, String i_timeslotFrom, ArrayList<String> i_teacher, String i_room, String i_course) {
		this.weekdayShortname = i_weekdayShortname;
		this.timeslotFrom = i_timeslotFrom;
		this.teacher = i_teacher;
		this.room = i_room;
		this.course = i_course;
		if(teacher.isEmpty() || room == "" || course == "") {
			this.color = false;
		} else {
			this.color = true;
		}
	}

	// default getter and setter are not commented
	
	public String getWeekdayShortname() {
		return weekdayShortname;
	}
	
	public void setWeekdayShortnme(String weekdayShortname) {
		this.weekdayShortname = weekdayShortname;
	}
	
	public String getTimeslotFrom() {
		return timeslotFrom;
	}
	
	public void setTimeslotFrom(String timeslotFrom) {
		this.timeslotFrom = timeslotFrom;
	}
	
	public ArrayList<String> getTeacher() {
		return teacher;
	}

	public void setTeacher(ArrayList<String> teacher) {
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
