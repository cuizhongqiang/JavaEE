package com.cbmie.patterns.decorator;
public class Bird extends Change {
    
    public Bird(TheGreatestSage sage) {
        super(sage);
    }

    @Override
    public void move() {
        // 代码
    	super.move();
    	System.out.println("Bird Move");
    }
}