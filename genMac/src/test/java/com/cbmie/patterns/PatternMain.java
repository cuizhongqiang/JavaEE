package com.cbmie.patterns;

import com.cbmie.patterns.decorator.Bird;
import com.cbmie.patterns.decorator.Fish;
import com.cbmie.patterns.decorator.Monkey;
import com.cbmie.patterns.decorator.TheGreatestSage;

public class PatternMain {
	public static void main(String[] args) {
		TheGreatestSage sage = new Monkey();
		// 第一种写法
		TheGreatestSage bird = new Bird(sage);
		TheGreatestSage fish = new Fish(bird);
		// 第二种写法
		// TheGreatestSage fish = new Fish(new Bird(sage));
		fish.move();
	}
}
