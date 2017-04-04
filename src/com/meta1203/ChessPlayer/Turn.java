package com.meta1203.ChessPlayer;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/*
 * Lovingly yanked from https://github.com/gt4dev/yet-another-tree-structure
 */

public class Turn implements Comparable <Turn>, Comparator<Turn> {

	private Board board;
	private Turn parent;
	private boolean whiteTurn;
	private List<Turn> children;
	private int depth;

	public Turn(Board data) {
		this.board = data;
		this.children = new LinkedList<Turn>();
		this.depth = 1;
	}
	
	public Turn(Board data, int depth, boolean white) {
		this.board = data;
		this.children = new LinkedList<Turn>();
		this.depth = depth;
	}

	public Turn addChild(Board child) {
		Turn childNode = new Turn(child, this.depth + 1, !this.whiteTurn);
		childNode.parent = this;
		this.children.add(childNode);
		return childNode;
	}
	
	public void sort() {
		if (children.isEmpty()) return;
		Collections.sort(children);
		for (Turn x : children) {
			x.sort();
		}
	}
	
	// Getters
	public Board getBoard() {
		return board;
	}
	public Turn getParent() {
		return parent;
	}
	public List<Turn> getChildren() {
		return children;
	}
	public boolean isWhiteTurn() {
		return whiteTurn;
	}
	
	// Comparison
	@Override
	public int compare(Turn one, Turn another) {
		// Weigh both points and # of turns made
		return (one.board.getBlackPointValue()+(50-one.depth))-(another.board.getBlackPointValue()+(50-another.depth));
	}

	@Override
	public int compareTo(Turn other) {
		return this.compare(this, other);
	}
}