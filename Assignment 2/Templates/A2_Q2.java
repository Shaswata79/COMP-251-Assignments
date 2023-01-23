import java.util.*;

/**
 * Author: Shaswata Bhattacharyya
 * No collaborators
 */
public class A2_Q2 {


	public static void main(String[] args) {

		int[] plates = {900, 500, 498, 4};
		System.out.print(weight(plates));

	}

	private static ArrayList<Integer> calculateClosest(int[] plates) {
		ArrayList<Integer> sums = new ArrayList<>();
		int difference = getUpperBound(plates);
		int W = getUpperBound(plates);		//program will not work if value of a plate is extremely large.

		int[][] Table = new int[plates.length + 1][W + 1];
		for (int i = 0; i <= plates.length; i++) {
			Table[i][0] = 1;
		}



		for (int i = 1; i <= plates.length; i++) {	//for all inputs
			for (int w = 1; w <= (1000 + difference); w++) {			//for all sums from 0 to upperBound
				if (w >= plates[i-1]) {
					if(Table[i-1][w]==1){		//if weight exists
						Table[i][w] = 1;
					} else if ((Table[i-1][w - plates[i-1]])==1){		//if weight possible
						Table[i][w] = 1;
						if(Math.abs(w-1000) < difference){		//add to list of possible closest weights
							sums.clear();
							sums.add(w);
							difference = Math.abs(w-1000);
						}else if(Math.abs(w-1000) == difference){
							sums.add(w);
						}
					}else {				//if weight not possible
						Table[i][w] = 0;
					}
				} else {
					Table[i][w] = Table[i-1][w];
				}
			}
		}

		return sums;

	}



	private static int getUpperBound(int[] plates){
		ArrayList<Integer> biggerThan = new ArrayList<>();
		int smallest = Integer.MAX_VALUE;
		for(int i = 0; i < plates.length; i++){
			if(plates[i] > 1000){
				biggerThan.add(plates[i]);
			}
		}
		if(biggerThan.isEmpty()){
			return 10000;	//arbitrary number larger than 1000
		}
		for(Integer num : biggerThan){
			if(num < smallest){
				smallest = num;
			}
		}

		return smallest;
	}


	public static int weight(int[] plates) {
		ArrayList<Integer> sums = calculateClosest(plates);		//sums will always contain either 1 or 2 different values
		if(sums.size() == 1){
			return sums.get(0);
		}else if(Math.abs(sums.get(0)-1000) == Math.abs(sums.get(1)-1000)){
			if(sums.get(0) > sums.get(1)){
				return sums.get(0);
			}else {
				return sums.get(1);
			}
		}else{
			if(Math.abs(sums.get(0)-1000) < Math.abs(sums.get(1)-1000)){
				return sums.get(0);
			}else {
				return sums.get(1);
			}
		}
	}










	private static void maxHeapify(ArrayList<Integer> A, int i){
		int l = 2 * i + 1;
		int r = 2 * i + 2;
		int n = A.size();
		int largest;
		if (l < n && A.get(l)>A.get(i)) {
			largest = l;
		} else {
			largest = i;
		}

		if (r < n && A.get(r)>A.get(largest)) {
			largest = r;
		}

		if (largest != i){
			int temp = A.get(i);
			A.set(i, A.get(largest));
			A.set(largest, temp);
			maxHeapify(A, largest);
		}
	}

	private static void buildMaxHeap(ArrayList<Integer> A) {
		int n = A.size();
		int x = n / 2;

		for(int i = x; i >= 1; --i) {
			int y = i - 1;
			maxHeapify(A, y);
		}

	}

	private static ArrayList<Integer> heapSort(Integer[] integers) {
		ArrayList<Integer> returnList = new ArrayList();
		ArrayList<Integer> A = new ArrayList<>(Arrays.asList(integers));
		buildMaxHeap(A);

		for(int i = A.size() - 1; i >= 1; --i) {
			int temp = A.get(i);
			A.set(i, A.get(0));
			A.set(0, temp);
			int removedInt = A.remove(i);
			returnList.add(removedInt);
			maxHeapify(A, 0);
		}

		if (A.size() >= 1) {
			returnList.add(A.get(0));
		}

		return returnList;
	}






}
