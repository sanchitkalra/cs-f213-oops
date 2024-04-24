package assignment3;

import java.io.*;
import java.util.*;
import java.util.Comparator;

enum Branches {
    CSIS, EEE, Mechanical, Chemical, Civil, Maths, Biology, Physics, Chemistry, Pharmacy, EcoFin, HSS
}

class Student implements Serializable {
    private int id;
    private String name;
    private Branches branch;
    private double cgpa;

    public Student(int id, String name, Branches branch, double cgpa) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.cgpa = cgpa;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Branches getProgramme() {
        return branch;
    }

    public double getCgpa() {
        return cgpa;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + branch + " " + String.format("%.2f", cgpa);
    }
}

class DuplicateIDException extends Exception {
    public DuplicateIDException(String message) {
        super(message);
    }
}

class NonExistentIDException extends Exception {
    public NonExistentIDException(String message) {
        super(message);
    }
}

class StudentRecordOps {
    public static void insert(ArrayList<Student> arr, Student s) throws DuplicateIDException {
        for (Student student : arr) {
            if (student.getId() == s.getId()) {
                throw new DuplicateIDException(String.valueOf(s.getId()));
            }
        }
        arr.add(s);
        System.out.println("Insert OK id: " + s.getId());
    }

    public static void delete(ArrayList<Student> arr, int id) throws NonExistentIDException {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getId() == id) {
                arr.remove(i);
                System.out.println("Delete OK id: " + id);
                return;
            }
        }
        throw new NonExistentIDException("Non-existent ID " + id);
    }

    public static void display(ArrayList<Student> arr, int id) throws NonExistentIDException {
        for (Student s : arr) {
            if (s.getId() == id) {
                System.out.println(s);
                return;
            }
        }
        throw new NonExistentIDException("Non-existent ID " + id);
    }

    public static void stats(ArrayList<Student> arr) {
        double sum = 0;
        for (Student s : arr) {
            sum += s.getCgpa();
        }
        double avg = arr.size() > 0 ? sum / arr.size() : 0;
        System.out.println("#records: " + arr.size() + "; avg CGPA: " + String.format("%.2f", avg));
    }

    public static void save(Student[] s_arr, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename, false)) {
            String op = "";
            for (int k = 0; k < s_arr.length && s_arr[k] != null; k++) {
                op += s_arr[k].getId() + " " + s_arr[k].getName() + " " + String.format("%.2f", s_arr[k].getCgpa())
                        + '\n';
            }
            fos.write(op.getBytes());
            fos.flush();

            System.out.println("Save OK filename: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException:Error in IO");
            return;
        }
    }

    public static void dump(ArrayList<Student> arr) {
        arr.sort(Comparator.comparingInt(Student::getId));
        for (Student student : arr) {
            System.out.println(student);
        }
    }
}

public class Record2 {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();

        // Open the file
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into words
                String[] words = line.split(" ");

                // Parse the command and arguments
                String command = words[0];

                switch (command) {
                    case "insert":
                        int id = Integer.parseInt(words[1]);
                        String name = words[2];
                        Branches branch = Branches.valueOf(words[3]);
                        double cgpa = Double.parseDouble(words[4]);
                        try {
                            StudentRecordOps.insert(students, new Student(id, name, branch, cgpa));
                        } catch (DuplicateIDException e) {
                            System.out.println("Exception: Duplicated ID " + e.getMessage());
                        }
                        break;
                    case "delete":
                        id = Integer.parseInt(words[1]);
                        try {
                            StudentRecordOps.delete(students, id);
                        } catch (NonExistentIDException e) {
                            System.out.println("Exception: " + e.getMessage());
                        }
                        break;
                    case "display":
                        id = Integer.parseInt(words[1]);
                        try {
                            StudentRecordOps.display(students, id);
                        } catch (NonExistentIDException e) {
                            System.out.println("Exception: " + e.getMessage());
                        }
                        break;
                    case "dump":
                        StudentRecordOps.dump(students);
                        break;
                    case "stats":
                        StudentRecordOps.stats(students);
                        break;
                    case "save":
                        String filename = words[1];
                        StudentRecordOps.save(students.toArray(new Student[students.size()]), filename);
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
