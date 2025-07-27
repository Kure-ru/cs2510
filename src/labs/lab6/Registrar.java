package labs.lab6;

import tester.Tester;

interface IPred<T> {
    boolean apply(T t);
}

class CourseHasStudent implements IPred<Course> {
    Student s;

    CourseHasStudent(Student s) {
        this.s = s;
    }

    public boolean apply(Course c) {
        return c.students.contains(s);
    }
}

interface IList<T> {
    boolean contains(T t);

    boolean hasCommonElement(IList<T> otherCourses);

    int countElement(IPred<T> pred);
}


class MtList<T> implements IList<T> {
    public boolean contains(T t) {
        return false;
    }

    public boolean hasCommonElement(IList<T> otherCourses) {
        return false;
    }

    public int countElement(IPred<T> pred) {
        return 0;
    }
}

class ConsList<T> implements IList<T> {
    T first;
    IList<T> rest;

    ConsList(T first, IList<T> rest) {
        this.first = first;
        this.rest = rest;
    }

    public boolean contains(T t) {
        return this.first.equals(t) || this.rest.contains(t);
    }

    public boolean hasCommonElement(IList<T> otherCourses) {
        return otherCourses.contains(this.first) || this.rest.hasCommonElement(otherCourses);
    }

    public int countElement(IPred<T> pred) {
        if (pred.apply(this.first)) {
            return 1 + this.rest.countElement(pred);
        } else {
            return this.rest.countElement(pred);
        }
    }
}

class Instructor {
    String name;
    IList<Course> courses;

    Instructor(String name) {
        this.name = name;
        this.courses = new MtList<>();
    }

    boolean dejavu(Student s) {
        return this.courses.countElement(new CourseHasStudent(s)) > 1;
    }
}

class Student {
    String name;
    int id;
    IList<Course> courses;

    Student(String name, int id) {
        this.name = name;
        this.id = id;
        this.courses = new MtList<>();
    }

    void enroll(Course c) {
        c.addStudent(this);
        this.courses = new ConsList<>(c, this.courses);
    }

    boolean classmates(Student s) {
        return classmatesHelper(this.courses, s.courses);
    }

    private boolean classmatesHelper(IList<Course> thisStudentCourses, IList<Course> otherCourses) {
        return thisStudentCourses.hasCommonElement(otherCourses);
    }


}


class Course {
    String name;
    Instructor prof;
    IList<Student> students;

    Course(String name, Instructor prof) {
        this.name = name;
        this.prof = prof;
        this.students = new MtList<>();
        this.prof.courses = new ConsList<>(this, this.prof.courses);
    }

    void addStudent(Student s) {
        this.students = new ConsList<>(s, this.students);
    }

}

class ExamplesClass {
    Student alice = new Student("Alice", 1);
    Student bob = new Student("Bob", 2);
    Student charlie = new Student("Charlie", 3);
    Student dave = new Student("Dave", 4);
    Student eve = new Student("Eve", 5);

    Instructor profSmith = new Instructor("Prof. Smith");
    Instructor profJones = new Instructor("Prof. Jones");

    Course cs101 = new Course("CS101", profSmith);
    Course cs102 = new Course("CS102", profSmith);
    Course cs103 = new Course("CS103", profJones);
    Course cs104 = new Course("CS104", profJones);

    void testEnroll(Tester t) {
        // enroll alice to CS101
        this.alice.enroll(cs101);
        t.checkExpect(this.cs101.students.contains(alice), true);
        t.checkExpect(alice.courses.contains(cs101), true);
    }

    void testClassmates(Tester t) {
        // enroll bob to CS101 and check if they share the same course with Alice
        this.alice.enroll(cs101);
        t.checkExpect(this.alice.classmates(bob), false);
        this.bob.enroll(cs101);
        t.checkExpect(this.alice.classmates(bob), true);
    }

    void testDejavu(Tester t) {
        // enroll Charlie to CS101 and CS102
        this.charlie.enroll(cs101);
        this.charlie.enroll(cs102);

        // check if prof.Smith has seen Charlie before
        t.checkExpect(this.profSmith.dejavu(this.charlie), true);
        // check if prof.Jones has seen Charlie before
        t.checkExpect(this.profJones.dejavu(this.charlie), false);
    }
}

