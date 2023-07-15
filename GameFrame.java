import javax.swing.JFrame;

public class GameFrame extends JFrame{

	GameFrame(){
		
		
		this.add(new GamePanel());
		this.setTitle("³g¦Y³D");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
	}

}
