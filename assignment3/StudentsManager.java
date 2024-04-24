package assignment3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

enum Programme {
    CSIS, EEE, Mechanical, Chemical, Civil, Maths, Biology, Physics, Chemistry, Pharmacy, EcoFin, HSS
}

class Student {
    int id;
    String name;
    Programme branch;
    double cgpa;

    Student(int id, String name, Programme branch, double cgpa) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.cgpa = cgpa;
    }
}

class StudentRecordOps {
    public static void debug(Student[] s_arr) {
        int k = 0;
        while (s_arr[k] != null) {
            System.out.print(s_arr[k].id + " ");
            k++;
        }
        System.out.println();
    }

    private static boolean findIfAlreadyExists(Student[] s_arr, Student student) {
        int k = 0;
        while (s_arr[k] != null) {
            if (s_arr[k].id == student.id)
                return true;
            k++;
        }

        return false;
    }

    public static void insert(Student[] s_arr, Student new_student) {
        boolean insertPos = findIfAlreadyExists(s_arr, new_student);
        if (insertPos == false) {
            int size = 0;
            while (s_arr[size] != null) {
                size++;
            }

            int index = 0;
            while (s_arr[index] != null && s_arr[index].id < new_student.id) {
                index++;
            }

            for (int i = size; i > index; i--) {
                s_arr[i] = s_arr[i - 1];
            }

            s_arr[index] = new_student;
            System.out.println("Insert OK id: " + new_student.id);
        } else {
            System.out.println("Exception: Duplicate ID " + new_student.id);
        }

    }

    private static int findDeleteIndex(Student[] s_arr, int id) {

        int size = 0;
        while (s_arr[size] != null) {
            size++;
        }

        int low = 0;
        int high = size - 1;

        if (low == high)
            return -1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (s_arr[mid].id == id) {
                return mid;
            } else if (s_arr[mid].id < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    public static void delete(Student[] s_arr, int id) {
        int deleteIndex = findDeleteIndex(s_arr, id);
        if (deleteIndex >= 0) {

            int size = 0;
            while (s_arr[size] != null) {
                size++;
            }
            size++;

            for (int i = deleteIndex; i < size - 1; i++) {
                s_arr[i] = s_arr[i + 1];
            }

            System.out.println("Delete OK id: " + id);
        } else {
            System.out.println("Exception: Non-existent ID " + id);
        }

    }

    public static void display(Student[] s_arr, int id) {
        boolean found = false;
        // for (int k = 0; k < s_arr.length; k++) {
        // if (s_arr[k].id == id) {
        // found = true;

        // // 1 Sunil CSIS 8.40
        // System.out.println(id + " " + s_arr[k].name + " " + String.format("%.2f",
        // s_arr[k].cgpa));

        // break;
        // }
        // }
        int k = 0;
        while (s_arr[k] != null) {
            if (s_arr[k].id == id) {
                found = true;
                System.out.println(id + " " + s_arr[k].name + " " + s_arr[k].branch.name() + " "
                        + String.format("%.2f", s_arr[k].cgpa));
                break;
            }
            k++;
        }
        if (!found) {
            System.out.println("Exception: Non-existent ID " + id);
        }
    }

    public static void stats(Student[] s_arr) {
        // #records: 2; avg CGPA: 7.70
        int count = 0;
        double avg = 0.0;

        int k = 0;
        while (s_arr[k] != null) {
            avg += s_arr[k].cgpa;
            k++;
            count++;
        }

        if (count == 0) {
            System.out.println("#records: 0; avg CGPA: 0.00");
        } else {
            System.out.println("#records: " + count + "; avg CGPA: " + String.format("%.2f", avg /= count));
        }
    }

    public static void save(Student[] s_arr, String filename) {
        try (FileOutputStream fos = new FileOutputStream(filename, false)) {
            int k = 0;
            String op = "";
            while (s_arr[k] != null) {
                op += s_arr[k].id + " " + s_arr[k].name + " " + s_arr[k].branch.name() + " "
                        + String.format("%.2f", s_arr[k].cgpa);
                k++;
            }
            fos.write(op.getBytes());
            fos.flush();

            System.out.println("Save OK filename: " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException: Error in IO");
            return;
        }
    }

    public static void dump(Student[] s_arr) {
        int k = 0;
        while (s_arr[k] != null) {
            System.out.println(s_arr[k].id + " " + s_arr[k].name + " " + s_arr[k].branch.name() + " "
                    + String.format("%.2f", s_arr[k].cgpa));
            k++;
        }
    }
}

public class StudentsManager {
    static String getCommand(String line) {
        String[] elements = line.split(" ");
        return elements[0];
    }

    static Programme mapProgramme(String program) {
        switch (program) {
            case "CSIS":
                return Programme.CSIS;

            case "EEE":
                return Programme.EEE;

            case "Mechanical":
                return Programme.Mechanical;

            case "Chemical":
                return Programme.Chemical;

            case "Civil":
                return Programme.Civil;

            case "Maths":
                return Programme.Maths;

            case "Biology":
                return Programme.Biology;

            case "Physics":
                return Programme.Physics;

            case "Chemistry":
                return Programme.Chemistry;

            case "Pharmacy":
                return Programme.Pharmacy;

            case "EcoFin":
                return Programme.EcoFin;

            case "HSS":
                return Programme.HSS;

            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Student arr[] = new Student[1000];

        try (FileInputStream fis = new FileInputStream(args[0])) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            while (reader.ready()) {
                String[] elements;
                String line = reader.readLine();
                int id;
                String name;
                Programme programme;
                Double cgpa;

                switch (getCommand(line)) {
                    case "insert":
                        elements = line.split(" ");
                        id = Integer.parseInt(elements[1]);
                        name = elements[2];
                        programme = mapProgramme(elements[3]);
                        cgpa = Double.parseDouble(elements[4]);

                        Student s = new Student(id, name, programme, cgpa);

                        StudentRecordOps.insert(arr, s);

                        // StudentRecordOps.debug(arr);
                        break;

                    case "delete":
                        elements = line.split(" ");
                        id = Integer.parseInt(elements[1]);

                        StudentRecordOps.delete(arr, id);

                        // StudentRecordOps.debug(arr);
                        break;

                    case "display":
                        elements = line.split(" ");
                        id = Integer.parseInt(elements[1]);

                        StudentRecordOps.display(arr, id);
                        break;

                    case "stats":
                        StudentRecordOps.stats(arr);
                        break;

                    case "save":
                        elements = line.split(" ");
                        StudentRecordOps.save(arr, elements[1]);
                        break;

                    case "dump":
                        StudentRecordOps.dump(arr);
                        break;

                    default:
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException: Error opening the specified file");
            return;
        } catch (IOException e) {
            System.out.println("IOException: Error in IO");
            return;
        }
    }
}
