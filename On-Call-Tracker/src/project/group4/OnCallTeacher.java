package project.group4;

public class OnCallTeacher extends Teacher
{
	private Schedule dailySchedule;
	private String weeklyTallyCount;
	private String monthlyTallyCount;
	private String termTallyCount;
	private AbsenceTracker submittedAbsence;
	private boolean absent;
	private boolean assigned;
	public static String weekMax = "2.0";
	public static String monthMax = "4.0";
	
	public OnCallTeacher(String NAME, String ID, Schedule dailySchedule) 
	{
		super(NAME,ID);
		this.dailySchedule = dailySchedule;
		this.absent = false;
		this.assigned = false;
	
	}
	
	public static void setWeekMax(String num)
	{
		weekMax = num;
	}
	
	public static void setMonthMax(String num)
	{
		monthMax = num;
	}
	
	public void hasBeenAssigned()
	{
		this.assigned = true;
	}
	
	
	public boolean getHasBeenAssigned()
	{
		return this.assigned;
	}
	
	public int getSparePeriodByIndex()
	{
		return this.dailySchedule.determineSparePeriodByIndex();
	}
	
	public int getLunchPeriodByIndex()
	{
		return this.dailySchedule.determineLunchPeriodByIndex();
	}
	
	public String getSparePeriodByValue()
	{
		return this.dailySchedule.getSpareByString();
	}
	
	public void setToAbsent(){
		absent = true;
	}
	
	public void submitAbsenceSchedule(AbsenceTracker submittedAbsence)
	{
		if(this.absent = true)
		{
			this.submittedAbsence = submittedAbsence;
		}
	}

	public AbsenceTracker getSubmittedAbsenceSchedule()
	{
		return this.submittedAbsence;
	}
	
	public boolean getAbsentStatus()
	{
		return absent;
	}
	
	public Schedule getSchedule() 
	{
		return dailySchedule;
	}
	
	public void setWeeklyTally(String count)
	{
		 weeklyTallyCount = count;
	}
	
	public void setMonthlyTally(String count)
	{
		 monthlyTallyCount = count;
	}
	
	public void setTermTally(String count) 
	{
		 termTallyCount = count;
	}

	public void incrementWeeklyTally()
	{
		double i = Double.parseDouble(weeklyTallyCount);
		i++;
		setWeeklyTally(String.valueOf(i));
	}
	
	public void incrementMonthlyTally()
	{
		double i = Double.parseDouble(monthlyTallyCount);
		i++;
		setMonthlyTally(String.valueOf(i));
	}
	
	public void incrementTermTally()
	{
		double i = Double.parseDouble(termTallyCount);
		i++;
		setTermTally(String.valueOf(i));
	}
	
	public String getWeeklyTally()
	{
		return weeklyTallyCount;
	}
	
	public String getMonthlyTally() 
	{
		return monthlyTallyCount;
	}
	
	public String getTermTally() 
	{
		return termTallyCount;
	}
	
	public boolean hasReachedweeklyMax()
	{
		double max = Double.parseDouble(weekMax);
		double currentCount = Double.parseDouble(weeklyTallyCount);
		return max <= currentCount;
	}
	
	public boolean hasReachedMonthlyMax()
	{
		double max = Double.parseDouble(monthMax);
		double currentCount = Double.parseDouble(monthlyTallyCount);
		return max <= currentCount;
	}
	
	public String toString()
	{
		String result= super.toString();
		result += "\nTERM SCHEDULE: "+ dailySchedule.toString();
		
		if(absent)
		{
			result += "\nABSENT" + submittedAbsence.toString();
		}
		
		result += "\nCOUNTS: WeeklyTally: " + weeklyTallyCount + " ";
		result += "MonltyTally: " + monthlyTallyCount + " ";
		result += "TermTally: " + termTallyCount +" \n";
		result += "Assigned" + getHasBeenAssigned();
		return result;
	}
}
