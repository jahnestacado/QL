package org.uva.sea.ql.gui.qlform;

import java.awt.Dimension;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.gui.qlform.interpreter.VariableUpdater;
import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

@SuppressWarnings("serial")
public abstract class QLConditionalBody extends JPanel implements Observer, QLContainerComponent{

	private final List<JPanel> questionPanelList;
	private  Map<String,Value> runTimeValues;
	private final VariableUpdater varUpdater;
	private final Expr condition;
	private final boolean isElseBody;

	
	public QLConditionalBody(List<JPanel> questionPanelList,Expr condition,VariableUpdater varUpdater,Map<String,Value> runTimeValues,boolean isElseBody){
	    super(new MigLayout("fill,insets 0"));
		this.questionPanelList=questionPanelList;
		this.runTimeValues=runTimeValues;
		this.varUpdater=varUpdater;
		this.varUpdater.addObserver(this);
		this.condition=condition;
		this.isElseBody=isElseBody;	
		setSettings();
	}
	
	
	
	private void setSettings(){
		this.setMinimumSize(new Dimension(617, 40));
		this.setOpaque(false);
		fillPanel();
		setVisibility(runTimeValues);
	}
	
	
	private void fillPanel(){
		for(JPanel questionPanel:questionPanelList){
			this.add(questionPanel,"align label,wrap");
		}
	}
	
	
	public JPanel getPanel(){
		return this;
	}


	@Override
	public void update(Observable arg0, Object arg1) {
		setVisibility(varUpdater.getUpdatedValues());
			
	}
	
	private void setVisibility(Map<String,Value> runTimeValues){
		boolean isVisible=((BoolValue) ExprEvaluator.eval(condition, runTimeValues)).getValue();
		if(isElseBody){
		this.setVisible(!isVisible);
		return;
		}
		this.setVisible(isVisible);
	}
	
	
	
	
}
