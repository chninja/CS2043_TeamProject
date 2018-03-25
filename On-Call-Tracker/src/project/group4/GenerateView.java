package project.group4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class GenerateView {

	public static Vector<Vector<String>> generateCoverageView(ArrayList<OnCallTeacher> teacherList, ArrayList<Teacher> supplyList)
	{
		Vector<Vector<String>> allData = new Vector<Vector<String>>();
		Vector<String> perRowData; 
		Schedule schedule = new Schedule("p1", "p2", "p3a", "p3b", "p4");
		for(int i = 0; i < teacherList.size(); i++){
			perRowData = new Vector<String>();
			//Only search those who have their absences marked
			if(teacherList.get(i).getAbsentStatus()){
				
				OnCallTeacher teacher = teacherList.get(i);
				
				//Get teacher absent schedule
				AbsenceTracker obj1 = teacher.getSubmittedAbsenceSchedule();
				
				int periodIndex = 0;
				String coveredBy ="";
				while(periodIndex <5){
					perRowData = new Vector<String>();
					String value = obj1.getPeriodValueAtIndex(periodIndex);
					
					//Check for slots with assigned code
					if(!(value.equals("0.0")) && !(value.equals("X"))){
						
						//Check for matches
						//If OnCall check teacherList
						if(value.charAt(0) == 'A'){
							for(int k = 0; k < teacherList.size();k++){
								if(teacherList.get(k).getID().equals(value)){
									coveredBy = teacherList.get(k).getName();
								}	
							}
						}
						//Check supplyList
						else{
							
							for(int k = 0; k < supplyList.size();k++){
								if(supplyList.get(k).getID().equals(value)){
									coveredBy = supplyList.get(k).getName();
								}	
							}
						}
					
			
					System.out.println("Period: " + periodIndex + " Absent " + teacherList.get(i).getName() + "CoveredBy: " + coveredBy);
					
					perRowData.add(schedule.convertIndexToPeriod(periodIndex));
					perRowData.add(teacherList.get(i).getName());
					String subject = teacherList.get(i).getSchedule().getSubject(periodIndex);
					String roomNum = teacherList.get(i).getSchedule().getRoomNumber(periodIndex);
					System.out.println("Subject" + subject+ roomNum);
					perRowData.add(coveredBy);
					//perRowData.add(subject);
					perRowData.add(roomNum);
					
					
					
					
					allData.add(perRowData);
					}
					periodIndex++;
					
				}
			}
		}
		return allData;
	}

	
	public static Vector<Vector<String>> generateCountView(ArrayList<OnCallTeacher> teacherList)
	{
		Vector<Vector<String>> allData = new Vector<Vector<String>>();
		Vector<String> perRowData = new Vector<String>();
		
		for(int i = 0; i < teacherList.size(); i++){
			perRowData = new Vector<String>();
			
			String name = teacherList.get(i).getName();
			String spare = teacherList.get(i).getSparePeriodByValue();
			String week = teacherList.get(i).getWeeklyTally();
			String month = teacherList.get(i).getMonthlyTally();
			String total = teacherList.get(i).getTermTally();
			
			perRowData.add(name);
			perRowData.add(spare);
			perRowData.add(week);
			perRowData.add(month);
			perRowData.add(total);
			allData.add(perRowData);
		}
		
		return allData;
	}
	
	
	public static Vector<Vector<String>> generateAvailabilityView(ArrayList<OnCallTeacher> teacherList)
	{
		
		
		Vector<Vector<String>> allData = new Vector<Vector<String>>();
		Vector<String> perRowData = new Vector<String>();
		int weeklyCount = 0;
		int monthlyCount = 0;
		Schedule schedule = new Schedule("p1", "p2", "p3a", "p3b", "p4");
	
	
		for(int periodIndex = 0; periodIndex < 5; periodIndex++)
		{
			perRowData = new Vector<String>();
			String nameNext = determineWhoIsNext(teacherList,periodIndex);
			for(int i = 0; i < teacherList.size(); i++){
				
				OnCallTeacher  teacher = teacherList.get(i);
				int spare = teacher.getSparePeriodByIndex();
				
				if(spare == periodIndex)
				{
					if(!teacherList.get(i).hasReachedweeklyMax() && !teacherList.get(i).hasReachedMonthlyMax()){
						System.out.println(periodIndex + " " + teacher.getName() + " "+ teacher.getWeeklyTally() + " " + teacher.getMonthlyTally());
						
							weeklyCount++;
							
						
					}
					if(!teacherList.get(i).hasReachedMonthlyMax()){
						monthlyCount++;
					}
					
				}
				
				
	
			}
			perRowData.add(schedule.convertIndexToPeriod(periodIndex));
			
			perRowData.add(String.valueOf(weeklyCount));
			perRowData.add(String.valueOf(monthlyCount));
			perRowData.add(nameNext);
			weeklyCount = 0;
			monthlyCount = 0;
			allData.add(perRowData);
		}
		
		return allData;
	}
	
	public static String determineWhoIsNext(ArrayList<OnCallTeacher> teacher, int periodIndex)
	{
		String name = "No one available";
		ArrayList<OnCallTeacher> potentials = new ArrayList<OnCallTeacher>();
		double lowest = findTheLowestTally(teacher, periodIndex);
		potentials = findPotentialNextInLines(teacher, lowest, periodIndex);
		//Collections.shuffle(potentials);
		if(!potentials.isEmpty())
		{
			name = potentials.get(0).getName();
		}
		
		
		return name;
	}
	
	public static ArrayList<OnCallTeacher> findPotentialNextInLines(ArrayList<OnCallTeacher> teacher,double lowest, int periodIndex)
	{
		String result = String.valueOf(lowest);
		ArrayList<OnCallTeacher> potentials = new ArrayList<OnCallTeacher>();
		for(int i = 0; i < teacher.size(); i++){
			
			int spare = teacher.get(i).getSparePeriodByIndex();
			
			if(spare == periodIndex)
			{
				if(!teacher.get(i).hasReachedMonthlyMax() && teacher.get(i).getWeeklyTally().equals(result))
				{
					potentials.add(teacher.get(i));
				}
			}
		}
		
		return potentials;
	}
	
	public static double findTheLowestTally(ArrayList<OnCallTeacher> teacher, int periodIndex)
	{
			double lowest = 9;
		
			for(int i = 0; i < teacher.size(); i++)
			{
				int spare = teacher.get(i).getSparePeriodByIndex();
				
				if(spare == periodIndex)
				{
					String thisTeachersCount = teacher.get(i).getWeeklyTally();
					double result = Double.parseDouble(thisTeachersCount);	
					
					if(lowest > result)
					{
						lowest = result;
					}
				}
				
			}
			return lowest;
		
		
		
	}
	
	
	public static void printData(Vector<Vector<String>> allData)
	{
		for(int i = 0; i < allData.size(); i++){
			for(int j = 0; j < allData.get(i).size(); j++){
			System.out.print(" " + allData.get(i).get(j));
			}
			System.out.println("");
		}
	}
	
	
	
}
