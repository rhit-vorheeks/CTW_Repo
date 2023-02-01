package GUI;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

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
	
	/**
	 * Wraps text
	 * @param i
	 * @param table
	 */
	public void wrapCol(int i, JTable table) {
		TableColumn column = table.getColumnModel().getColumn(i);
		column.setCellRenderer(new TextAreaRenderer());
		table.setRowHeight(0, 100);
	}

	
//	/**
//	 * Shows the contents on the page's frame.
//	 */
//	public void show() {}
}
