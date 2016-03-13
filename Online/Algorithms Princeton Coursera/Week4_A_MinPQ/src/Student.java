import java.util.Comparator;

public class Student implements Comparable<Student>{
		public String name;
		public int age;
		
		public static final Comparator<Student> BY_NAME = new ByName();
		public static final Comparator<Student> BY_AGE = new ByAge();
		
		
		
		public Student(String name,int age){
			this.name = name;
			this.age = age;
		}
		
		public void setName(String s){
			name = s;
		}
		
		public void setAge(int i){
			age = i;
		}
		
		public void display(){
			System.out.println("Name: " + name);
			System.out.println("Age: " + age);
			
		}
		private static class ByName implements Comparator<Student>{

			@Override
			public int compare(Student v, Student w) {
				// TODO Auto-generated method stub
				return v.name.compareTo(w.name);
			}
			
		}
		
		private static class ByAge implements Comparator<Student>{

			@Override
			public int compare(Student v, Student w) {
				// TODO Auto-generated method stub
				return v.age - w.age;
			}
			
		}
		
		@Override
		public int compareTo(Student o) {
			// TODO Auto-generated method stub

			//System.out.println("s = " + name + ", item  = " + o.name);
			
			return (name.toLowerCase()).compareTo(o.name.toLowerCase());
		}
		
		
		public String getName(){
			return name;
			
		}
		
		public int getAge(){
			return age;
		}

		
		
	}