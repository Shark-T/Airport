package resources;

import java.util.*;

/**
 * Created by andrew on 19.06.17.
 * <p>
 * Generate query string for filling Passengers in DB.
 */
public class GenerateNames {


    private static List femaleNames;
    private static List maleNames;
    private static List lastNames;
    private static String[] flightNumbers = {
            "EK 1271", "OA 7147", "ES 971", "AZ 1271", "XE 1057", "IQ 5712", "OA 7241",
            "ES 571", "AZ 974 ", "EQ 1091", " UK 5712", "PQ 213 ", "MN 981 ", "AZ 982 ", "JS 472 ", "SP 9013", " AU 717 ", "UK 124 ", "GR 148",
            "JS 225", "RU 9013", "NL 717", "IN 771", "TH 148", "MA 195"
    };

    public static void main(String[] args) {

        List gender = new ArrayList();
        gender.add("male");
        gender.add("female");


        String[] lastNamesArr = {

                "Smith", "Fox", "Klinton", "Brown", "Cruze", "Bigetti", "Cartman", "Broflowsky",
                "Maggy", "March", "Token", "Simpsons", "Stone", "McMerfy", "Taller", "Morrow", "White",
                "Kidmann", "Lee", "Forest", "Matsumoto", "Tenma", "Yagami"
        };


        lastNames = new ArrayList(Arrays.asList(lastNamesArr));


        for (String flightNumber : flightNumbers) {


            //lets produce a 50 persons

            for (int i = 0; i < 20; i++) {

                Collections.shuffle(gender);
                generateQuery((String) gender.get(0), flightNumber);


            }
        }
    }

    public static void generateQuery(String gender, String flightNumber) {

        String firstName;
        String lastName;


        if (gender.equals("male")) {
            firstName = GenerateNames.getMaleName();

        } else {
            firstName = GenerateNames.getFemaleName();
        }
        lastName = getLastName();


        System.out.println(String.format("INSERT INTO `passengers`(`flightNumber`, `firstName`, `lastName`, `nationality`, `passport`, " +
                        "`birthday`, `gender`, `class`) VALUES ('%1$s', '%2$s', '%3$s', '%4$s', '%5$s', '%6$s', '%7$s', '%8$s');",
                flightNumber, firstName, lastName, getNationality(), getPassport(), getAge(), gender, getFlightClass()));


    }

    public static String getMaleName() {

        String[] maleNamesArr = {
                "Jacob", "Michael", "Joshua", "Matthew", "Andrew", "Christopher",
                "Daniel", "Ethan", "Joseph", "William", "Anthony", "Nicholas", "David", "Alexande", "Ryan", "Tyler", "James", "John",
                "Jonathan", "Brandon", "Christian", "Dylan", "Zachary", "Noah", "Samuel", "Benjamin", "Nathan", "Logan", "Justin", "Jose",
                "Gabriel", "Austin", "Kevin", "Caleb", "Robert", "Elijah", "Thomas", "Jordan", "Cameron", "Hunter", "Jack", "Angel", "Isaiah",
                "Jackson", "Evan", "Luke", "Jason", "Isaac", "Mason", "Aaron"
        };

        maleNames = new ArrayList(Arrays.asList(maleNamesArr));

        Collections.shuffle(maleNames);
        return (String) maleNames.get(0);

    }


    public static String getFemaleName() {


        String[] femaleNamesArr = {"Emily", "Madison", "Emma", "Hannah", "Olivia", "Abigail", "Isabella", "Ashley", "Samantha", "Elizabeth", "Alexis",
                "Sarah", "Alyssa", "Grace", "Sophia", "Taylor", "Brianna", "Lauren", "Ava",
                "Kayla", "Jessica", "Natalie", "Chloe", "Anna", "Victoria", "Hailey", "Mia",
                "Sydney", "Jasmine", "Morgan", "Julia", "Destiny", "Rachel", "Megan", "Kaitlyn",
                "Katherine", "Jennifer", "Savannah", "Ella", "Alexandra",
                "Haley", "Allison", "Maria", "Nicole", "Mackenzie", "Brooke", "Makayla", "Kaylee", "Lily", "Stephanie"
        };

        femaleNames = new ArrayList(Arrays.asList(femaleNamesArr));
        Collections.shuffle(femaleNames);


        return (String) femaleNames.get(0);
    }

    public static String getLastName() {

        Collections.shuffle(lastNames);
        return (String) lastNames.get(0);

    }


    public static String getPassport() {

        Random r = new Random();
        int Low = 65;
        int High = 91;
        int literal = r.nextInt(High - Low) + Low;
        char ch = (char) literal;
        String passport = Character.toString(ch);

        for (int i = 0; i < 8; i++) {

            passport += Integer.toString(r.nextInt(10));
        }

        return passport;

    }

    public static String getNationality() {

        String[] nationalityArr = {
                "Japanese", "Ukrainian", "British", "American", "Australian", "Swedish", "Germanic",
                "Chinease"

        };

        List nationality = new ArrayList(Arrays.asList(nationalityArr));
        Collections.shuffle(nationality);

        return (String) nationality.get(0);

    }


    public static String getFlightClass() {

        String[] flightClass = {"business", "economy"};

        Random rand = new Random();

        return flightClass[rand.nextInt(2)];

    }


    public static String getAge() {

        Random r = new Random();
        int Low = 1945;
        int High = 1995;
        int year = r.nextInt(High - Low) + Low;
        int month;
        int day;
        while (true) {
            month = r.nextInt(13);
            day = r.nextInt(28);

            if (month != 0 && day != 0) {
                break;
            }
        }


        return String.format("%d-%02d-%02d", year, month, day);


    }

}