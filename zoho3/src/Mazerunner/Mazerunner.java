package Mazerunner;

import java.util.Random;
import java.util.Scanner;

public class Mazerunner {
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the no of rows:");
		int n=sc.nextInt();
		System.out.println("Enter the no of columns:");
		int m=sc.nextInt();
		if(n<=0||m<=0)
		{
			System.out.println("invalid input");
		}
		Maze maze=new Maze(n,m);
		System.out.println("Intialized matrix");
		maze.print();
		maze.placemonsters();
		maze.placetreasure();
		System.out.println("the matrix after them placing");
		maze.print();
		Random random=new Random();
		maze.shortestpath(random.nextInt(m),0);
	}//1,0
	//2,0
	//3,0

}
