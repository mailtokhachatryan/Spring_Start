package am.myOffice.shopJDBC.robot;

import am.myOffice.shopJDBC.robot.foot.Foot;
import am.myOffice.shopJDBC.robot.hand.Hand;
import am.myOffice.shopJDBC.robot.head.Head;

public class Robot {

    private Foot foot;
    private Hand hand;
    private Head head;

    public void walk() {
        foot.walk();
    }

    public void hit() {
        hand.hit();
    }

    public void think() {
        head.think();
    }

    public void setFoot(Foot foot) {
        this.foot = foot;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public void setHead(Head head) {
        this.head = head;
    }
}
