package nkher.datastructures.trees;

import java.util.Random;

import nkher.datastructures.lists.DynamicArray;
import nkher.interfaces.MyTreap;

public class Treap<K extends Comparable<K>, V> implements MyTreap<K, V> {
	
	private int size;
	private static Random rand = new Random();

	private TreapNode<K, V> root;
	
	public static class TreapNode<K extends Comparable<K>, V> {
		
		private int priority; // the numeric value that decides priority
		private K key;
		private V value;
		private TreapNode<K, V> left;
		private TreapNode<K, V> right;
		
		public TreapNode() {} /** default cons */
		
		public TreapNode(K key, V value) {
			this.key = key;
			this.value = value;
			this.priority = rand.nextInt(Integer.MAX_VALUE/2) + 1;
		}
		
		public TreapNode(K key, V value, TreapNode<K, V> left, TreapNode<K, V> right) {
			this.key = key; 
			this.value = value;
			this.left = left;
			this.right = right;
			this.priority = rand.nextInt(Integer.MAX_VALUE/2) + 1;
		}

		/** Getters  for key, value, left and right nodes */
		
		public K key() {
			return this.key;
		}
		
		public V value() {
			return this.value;
		}
		
		public TreapNode<K, V> left() {
			return left;
		}
		
		public TreapNode<K, V> right() {
			return right;
		}
		
		public int priority() {
			return this.priority;
		}
		
		/** Setters for left and right nodes */
		
		public void setLeft(TreapNode<K, V> node) {
			this.left = node;
		}
		
		public void setRight(TreapNode<K, V> node) {
			this.right = node;
		}
		
		/***
		 * Returns the max key in the the current node's subtree 
		 * 
		 * @return a key of type {@code K}
		 */
		public K max() {
			if (right == null) return key;
			return right.max();
		}
		
		/***
		 * Returns the maximum key node in the the current node's subtree 
		 * 
		 * @return a node of type {@code TreapNode<K, V>}
		 */
		public TreapNode<K, V> maxnode() {
			if (right == null) return this;
			return right.maxnode();
		}
		
		/***
		 * Returns the min key in the the current node's subtree 
		 * 
		 * @return a key of type {@code K}
		 */
		public K min() {
			if (left == null) return key;
			return left.min();
		}
		
		/***
		 * Returns the minimum key node in the the current node's subtree 
		 * 
		 * @return a node of type {@code TreapNode<K, V>}
		 */
		public TreapNode<K, V> minnode() {
			if (left == null) return this;
			return left.minnode();
		}
		
		public String toString() {
			return "[ key->" + this.key.toString() + ", value->" + this.value.toString() + ", priority->" + this.priority + " ]";
		}
		
		public boolean isLeaf() {
			return (left == null && right == null);
		}
		
	}
	
	@Override
	public void insert(K key, V val) {
		insert(new TreapNode<K, V>(key, val));
	}
	
	public void insert(TreapNode<K, V> node) {
		if (node == null) {
			throw new IllegalArgumentException("Cannot insert a null node in a treap.");
		}
		size++;
		root = insertHelper(root, node);
	}
	
	private TreapNode<K, V> insertHelper(TreapNode<K, V> root, TreapNode<K, V> node) {
		
		if (null == root) {
			root = node;
		} 
		else if (root.key.compareTo(node.key) > 0) { // root is greater
			root.left = insertHelper(root.left, node);
			if (root.left.priority < root.priority) {
				root = leftRotate(root);
			}			
		}
		else if (node.key.compareTo(root.key) > 0){ // root is smaller
			root.right = insertHelper(root.right, node);
			if (root.right.priority < root.priority) {
				root = rightRotate(root);
			}
		}
		
		return root;
	}

	@Override
	public void remove(K key) {		
	}

	@Override
	public boolean exists(K key) {
		return false;
	}

	@Override
	public K minKey() {
		TreapNode<K, V> temp = root;
		while (temp != null) {
			temp = temp.left;
		}
		return temp.key;
	}

	@Override
	public V minVal() {
		TreapNode<K, V> temp = root;
		while (temp != null) {
			temp = temp.left;
		}
		return temp.value;
	}

	@Override
	public K maxKey() {
		TreapNode<K, V> temp = root;
		while (temp != null) {
			temp = temp.right;
		}
		return temp.key;
	}

	@Override
	public V maxVal() {
		TreapNode<K, V> temp = root;
		while (temp != null) {
			temp = temp.right;
		}
		return temp.value;
	}

	@Override
	public void clear() {		
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	public TreapNode<K, V> root() {
		return root;
	}
	
	/*******************************************************************************
	 * Methods that perform necessary rotations for balancing the search tree.
	 * These are used to check balance property during insertion and deletion of nodes.
	 *******************************************************************************/
	
	private TreapNode<K, V> leftRotate(TreapNode<K, V> y) {
		TreapNode<K, V> x = y.right;
		TreapNode<K, V> subtree = x.left;
		
		/** Performing the rotation */
		x.left = y;
		y.right = subtree;
		
		return x;
	}
	
	private TreapNode<K, V> rightRotate(TreapNode<K, V> y) {
		TreapNode<K, V> x = y.left;
		TreapNode<K, V> subtree = x.right;
		
		/** Performing the rotation */
		x.right = y;
		y.left = subtree;
		
		return x;
	}

	/******************************
	 * TREE TRAVERSALS
	 ******************************/

	/***
	 * Function to get the nodes of the tree in in-order fashion.
	 * In-order -> left, vertex, right
	 * 
	 * @return - array of {@code DynamicArray<TreapNode<K, V>>} type
	 */
	public DynamicArray<TreapNode<K, V>> inorder() {
		DynamicArray<TreapNode<K, V>> dArray = new DynamicArray<TreapNode<K,V>>();
		if (this.root == null) return dArray;
		return inorderUtil(root, dArray);
	}
	
	private DynamicArray<TreapNode<K, V>> inorderUtil(TreapNode<K, V> node, DynamicArray<TreapNode<K, V>> dArray) {
		if (node != null) {
			inorderUtil(node.left, dArray);
			dArray.insert(node);
			inorderUtil(node.right, dArray);
		}
		return dArray;
	}
	

}
