package GUI;

import javax.swing.JFrame;

/**
 * Abstract Page used for methods that apply to all pages.
 *
 */
public abstract class AbstractPage {
	private JFrame frame;
	
	
	/**
	 * Constructor for AbstractPage
	 * @param frame
	 */
	public AbstractPage(JFrame frame) {
		this.frame = frame;
	}
	
	/**
	 * Returns the frame of the page.
	 * @return JFrame
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	/**
	 * Sets the frame to be not visible.
	 */
	public void hide() {
		frame.setVisible(false);
	}
	
	/**
	 * Clears all of the contents of the frame and repaints it.
	 */
	public void clear() {
		frame.getContentPane().removeAll();
		frame.repaint();
	}
	
//	/**
//	 * Shows the contents on the page's frame.
//	 */
//	public void show() {}
}
