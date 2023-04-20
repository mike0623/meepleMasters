package tw.com.eeit162.meepleMasters.lyh.controller;

import java.util.Random;

public class RandomCard {

	public int cardId() {

		// 設定每種卡片的機率（以百分比表示）
		int[] cardProbabilities = { 2, 6, 10, 22, 60 };

		// 計算所有卡片的總機率
		int totalProbability = 0;
		for (int i = 0; i < cardProbabilities.length; i++) {
			totalProbability += cardProbabilities[i];
		}

		// System.out.println(totalProbability);

		// 生成一個隨機數，表示抽到的卡片星數
		Random random = new Random();
		int randomNumber = random.nextInt(totalProbability);
		System.out.println("randomNumber: " + randomNumber);

		int cumulativeProbability = 0;
		int[] star = new int[5];

		for (int i = 0; i < cardProbabilities.length; i++) {
			cumulativeProbability += cardProbabilities[i];
			star[i] = cumulativeProbability;
//            System.out.println(cumulativeProbability);
		}

		int card = 0;
		
		// 輸出星數
		if (randomNumber < (star[0])) {
			System.out.println("五星");
			card = star5();
		} else if (randomNumber < (star[1])) {
			System.out.println("四星");
			card = star4();
		} else if (randomNumber < (star[2])) {
			System.out.println("三星");
			card = star3();
		} else if (randomNumber < (star[3])) {
			System.out.println("二星");
			card = star2();
		} else {
			System.out.println("一星");
			card = star1();
		}
		
		return card;
	}

	public int star5() {
		String[] card = new String[5];
		int[] cardId = {44, 45, 46, 47, 48};

		for (int i = 0; i < 5; i++) {
			card[i] = "5-" + (i + 1) + ".png";
		}

		Random random = new Random();
		int randomNumber = random.nextInt(card.length);

		System.out.println(card[randomNumber]);
		System.out.println(cardId[randomNumber]);
		return cardId[randomNumber];
	}

	public int star4() {
		String[] card = new String[4];
		int[] cardId = {40, 41, 42, 43};

		for (int i = 0; i < 4; i++) {
			card[i] = "4-" + (i + 1) + ".png";
		}

		Random random = new Random();
		int randomNumber = random.nextInt(card.length);

		System.out.println(card[randomNumber]);
		System.out.println(cardId[randomNumber]);
		return cardId[randomNumber];
	}

	public int star3() {
		String[] card = new String[5];
		int[] cardId = {35, 36, 37, 38, 39};

		for (int i = 0; i < 5; i++) {
			card[i] = "3-" + (i + 1) + ".png";
		}

		Random random = new Random();
		int randomNumber = random.nextInt(card.length);

		System.out.println(card[randomNumber]);
		System.out.println(cardId[randomNumber]);
		return cardId[randomNumber];
	}

	public int star2() {
		String[] card = new String[10];
		int[] cardId = {25, 26, 27, 28, 29, 30, 31, 32, 33, 34};

		for (int i = 0; i < 10; i++) {
			card[i] = "2-" + (i + 1) + ".png";
		}

		Random random = new Random();
		int randomNumber = random.nextInt(card.length);

		System.out.println(card[randomNumber]);
		System.out.println(cardId[randomNumber]);
		return cardId[randomNumber];
	}

	public int star1() {
		String[] card = new String[24];
		int[] cardId = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};

		for (int i = 0; i < 24; i++) {
			card[i] = "1-" + (i + 1) + ".png";
		}

		Random random = new Random();
		int randomNumber = random.nextInt(card.length);

		System.out.println(card[randomNumber]);
		System.out.println(cardId[randomNumber]);
		return cardId[randomNumber];
	}

}
