import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

class BabyBirths {

    
    public static int getNumberOfNamesUsingFile(String gender){
		FileResource fr = new FileResource();
        int count=0;
        for(CSVRecord record:fr.getCSVParser(false)){
            if(record.get(1).equals(gender)){
                count++;
            }
        }
        return count;
    }

    public static int getRankUsingFile(File f,String name,String gender){
		FileResource fr = new FileResource(f);
        int rank=0;
        for(CSVRecord record:fr.getCSVParser(false)){
            if(record.get(1).equals(gender)){
                rank++;
                if(record.get(0).equals(name) ){
                    return rank;
                }
            }
        }
        return -1;
    }


	public static void printNames () {
		FileResource fr = new FileResource();
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			if (numBorn <= 100) {
				System.out.println("Name " + rec.get(0) +" Gender " + rec.get(1) +" Num Born " + rec.get(2));
			}
		}
	}

    public static int getRank(int yearNum,String name,String gender){
        String fileName="us_babynames_by_year/yob"+yearNum+".csv";
		FileResource fr = new FileResource(fileName);
        int rank=0;
        for(CSVRecord record:fr.getCSVParser(false)){
            if(record.get(1).equals(gender)){
                rank++;
                if(record.get(0).equals(name) ){
                    return rank;
                }
            }
        }
        return -1;
    }

    public static String getName(int yearNum,int rank,String gender){
        String fileName="us_babynames_by_year/yob"+yearNum+".csv";
		FileResource fr = new FileResource(fileName);
        int r=0;
        for(CSVRecord record:fr.getCSVParser(false)){
            if(record.get(1).equals(gender)){
                r++;
                if(r==rank){
                    return record.get(0);
                }
            }
        }
        return "NO NAME";
    }

    
    public static int getNameNumbers(int yearNum,int rank,String gender){
        String fileName="us_babynames_by_year/yob"+yearNum+".csv";
		FileResource fr = new FileResource(fileName);
        int r=0;
        for(CSVRecord record:fr.getCSVParser(false)){
            if(record.get(1).equals(gender)){
                r++;
                if(r==rank){
                    return Integer.parseInt(record.get(2));
                }
            }
        }
        return -1;
    }

    public static String yearOfHighestRank(String name,String gender){
        DirectoryResource dr = new DirectoryResource();
        int highestRankForName=Integer.MAX_VALUE;
        String fileName="";
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
            int rank=getRankUsingFile(f, name, gender);
            // System.out.println(rank+":"+f.getName());
            if(highestRankForName>rank && rank!=-1){
                highestRankForName=rank;
                fileName=f.getName();
            }
        }
        return fileName.substring(3,7);
        // return fileName;
    }

    public static int getTotalBirthsRankedHigher(int yearNum,String name,String gender) {
        int rank=getRank(yearNum, name, gender);
        int total=0;
        for(int i=rank-1;i>0;i--){
            int res=getNameNumbers(yearNum,i, gender);
            total+=res;
        }
        return total;
    }

    public static float getAverageRank(String name,String gender){
        DirectoryResource dr = new DirectoryResource();
        float totalRank=0;
        float numOfRanks=0;
		for (File f : dr.selectedFiles()) {
			FileResource fr = new FileResource(f);
            int rank=getRankUsingFile(f, name, gender);
            totalRank+=rank;
            numOfRanks++;
        }
        float average=totalRank/numOfRanks;
        return average ;
    }

    public static String whatIsNameInYear(int yearNum,int newYear,String name,String gender){
        int rank= getRank(yearNum, name, gender);
        String nameIfBornInYear=getName(newYear, rank, gender);
        return nameIfBornInYear;
    }


	public static void totalBirths (FileResource fr) {
		int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;
		for (CSVRecord rec : fr.getCSVParser(false)) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
			}
			else {
				totalGirls += numBorn;
			}
		}
		System.out.println("total births = " + totalBirths);
		System.out.println("female girls = " + totalGirls);
		System.out.println("male boys = " + totalBoys);
	}

	public static void testTotalBirths () {
		//FileResource fr = new FileResource();
        int yearNum=2014;
        String fileName="data/yob"+yearNum+".csv";
		FileResource fr = new FileResource(fileName);
		totalBirths(fr);
	}

    
	public static void main(String[] args) {
        // FileResource fr = new FileResource();
        // System.out.println(getNumberOfNamesUsingFile("F"));

        // int rank= getRank(1971, "Frank", "M");
        // System.out.println(rank);
        
        // String name= getName(1982, 450, "M");
        // System.out.println(name);

        // String nameIf= whatIsNameInYear(1974, 2014,"Owen", "M");
        // System.out.println(nameIf);

        // String year=yearOfHighestRank("Genevieve", "F");
        // System.out.println(year);

        // float average=getAverageRank("Robert", "M");
        // System.out.println(average);
        
        // int total=getTotalBirthsRankedHigher(1990,"Drew", "M");
        // System.out.println(total);

	}
}
