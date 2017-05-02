package assignment10;

/**
*Description: This program will build a CourseGrades class to demonstrate inheritance
*Class: Fall - COSC 1437.81003
*Assignment10: CourseGrades Class
*Date: 12/2/2015
*@author  Jeremy Pierce
*@version 0.0.0
*/ 
public class CourseGrades {
	
	GradedActivity[] grades = new GradedActivity[4];
	
	/**
	 * 
	 * @param ga as a GradedActivity object
	 */
	public void setLab(GradedActivity ga){
		grades[0] = ga;
	}// end of setLab method
	
	/**
	 * 
	 * @param pfe as a PassFailExam object
	 */
	public void setPassFailExam(PassFailExam pfe){
		grades[1] = pfe;
	}// end of setPassFailExam method
	
	/**
	 * 
	 * @param ga as a GradedActivity object
	 */
	public void setEssay(GradedActivity ga){
		grades[2] = ga;
	}// end of setEssay method
	
	/**
	 * 
	 * @param fe as a FinalExam object
	 */
	public void setFinalExam(FinalExam fe){
		grades[3] = fe;
	}// end of setFinalExam method
	
	/**
	 * @return String as the scores and grades stored in the GradedActivity array
	 */
	public String toString(){
		String temp = "";
		
		for(int i = 0; i < grades.length; i++)
			temp += "Score: " + grades[i].getScore() + " Grade: " + grades[i].getGrade() + "\n";
		
		return temp;
	}// end of toString method

}// end of class
