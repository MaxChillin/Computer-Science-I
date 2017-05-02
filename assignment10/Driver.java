package assignment10;

/**
*Description: This program will build a Driver class to run the inheritance example from CourseGrades class
*Class: Fall - COSC 1437.81003
*Assignment10: Driver Class
*Date: 12/2/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class Driver {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		GradedActivity lab, passFailExam, essay, finalExam;
		CourseGrades cg;
		
		cg = new CourseGrades();
		lab = new GradedActivity();
		essay = new GradedActivity();
		passFailExam = new PassFailExam(50, 5, 35);
		finalExam = new FinalExam(50, 7);
		
		lab.setScore(89);
		essay.setScore(93);
		
		cg.setLab(lab);
		cg.setPassFailExam((PassFailExam)passFailExam);
		cg.setEssay(essay);
		cg.setFinalExam((FinalExam)finalExam);
		
		System.out.println(cg);

	}// end of main

}// end of class
