package org.uva.sea.ql.gui.qlform.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.uva.sea.ql.ast.expr.Expr;
import org.uva.sea.ql.ast.form.Body;
import org.uva.sea.ql.ast.form.BodyElement;
import org.uva.sea.ql.ast.form.ComputedQuestion;
import org.uva.sea.ql.ast.form.Form;
import org.uva.sea.ql.ast.form.IfThen;
import org.uva.sea.ql.ast.form.IfThenElse;
import org.uva.sea.ql.ast.form.Question;
import org.uva.sea.ql.ast.form.SingleLineElement;
import org.uva.sea.ql.ast.types.Type;
import org.uva.sea.ql.gui.qlform.ComputedQuestionPanel;
import org.uva.sea.ql.gui.qlform.QLElsePanel;
import org.uva.sea.ql.gui.qlform.QLIfThenPanel;
import org.uva.sea.ql.gui.qlform.QuestionPanel;
import org.uva.sea.ql.gui.qlform.renderer.Renderer;
import org.uva.sea.ql.visitor.IElementVisitor;
import org.uva.sea.ql.visitor.evaluator.ExprEvaluator;
import org.uva.sea.ql.visitor.evaluator.values.BoolValue;
import org.uva.sea.ql.visitor.evaluator.values.DecValue;
import org.uva.sea.ql.visitor.evaluator.values.IntValue;
import org.uva.sea.ql.visitor.evaluator.values.StrValue;
import org.uva.sea.ql.visitor.evaluator.values.Value;

public class SwingGenerator implements IElementVisitor{
	private final List<JPanel> questionPanelList;
	private final VariableUpdater varUpdater;
	private Map<String,Value> runTimeValues;
	

	public SwingGenerator(List<JPanel> questionPanelList,Map<String,Value> runTimeValues,VariableUpdater varUpdater){
		this.questionPanelList=questionPanelList;
		this.runTimeValues=runTimeValues;
		this.varUpdater=varUpdater;
	}
	
	
	public void generate(Form form, JFrame frame) {
		String formName=form.getId().getName();
		frame.setTitle(formName);
		form.accept(this);
		new Renderer(questionPanelList, frame,varUpdater).addQuestionsToPanel();

	}

	

	
	private void fillConditionalPanel(Body qlElement){
		qlElement.accept(this);
	}
	
	private List<JPanel> getConditionalBodyElements(Body qlElement){
		List<JPanel> bodyPanels=new ArrayList<JPanel>();
		SwingGenerator conditionalBodyGeneration=new SwingGenerator(bodyPanels,runTimeValues,varUpdater);
		conditionalBodyGeneration.fillConditionalPanel(qlElement);
		bodyPanels=conditionalBodyGeneration.getQuestionPanelList();
		return bodyPanels;
		
	}

	
	@Override
	public void visit(Form qlElement) {
		qlElement.getBody().accept(this);
	}

	@Override
	public void visit(Body qlElement) {
		List<BodyElement> bodyList = qlElement.getBodyElement();
		for (BodyElement element : bodyList) {
			element.accept(this);
		}

	}

	@Override
	public void visit(Question qlElement) {
		initVar(qlElement);
		addQuestion(qlElement, runTimeValues);
	}

	
	@Override
	public void visit(ComputedQuestion qlElement) {
		initVar(qlElement);   
		Value value = ExprEvaluator.eval(qlElement.getExpr(),runTimeValues);
		runTimeValues.put(qlElement.getId().getName(),value);

		addComputedQuestion(qlElement,runTimeValues);

	}
	
	
	@Override
	public void visit(IfThenElse qlElement) {
		Body ifBody = qlElement.getIfBody();
		Body elseBody = qlElement.getElseBody();
		Expr condition=qlElement.getCondition();
		
		List<JPanel> ifBodylist = getConditionalBodyElements(ifBody);
		questionPanelList.add(new QLIfThenPanel(ifBodylist, condition,varUpdater, runTimeValues).getPanel());
	

		List<JPanel> elseBodyList = getConditionalBodyElements(elseBody);
		questionPanelList.add(new QLElsePanel(elseBodyList, condition, varUpdater, runTimeValues).getPanel());

		
	}

	@Override
	public void visit(IfThen qlElement) {
		Body body = qlElement.getIfBody();
		Expr condition = qlElement.getCondition();
	    List<JPanel> list=getConditionalBodyElements(body);
		questionPanelList.add(new QLIfThenPanel(list, condition, varUpdater, runTimeValues).getPanel());

	}
	
	
	
	private void addQuestion(Question qlElement,Map<String,Value> declaredVar){
		QuestionPanel panel=new QuestionPanel(qlElement,declaredVar,varUpdater);
		questionPanelList.add(panel.getPanel());		
	}
	
	private void addComputedQuestion(ComputedQuestion qlElement,Map<String,Value> declaredVar){
		ComputedQuestionPanel panel=new ComputedQuestionPanel(qlElement, declaredVar,varUpdater);
		questionPanelList.add(panel.getPanel());
	}
	
	
	private void initVar(SingleLineElement qlElement){
		String varName=qlElement.getId().getName();
		if(runTimeValues.containsKey(varName)){
			Value value=runTimeValues.get(varName);
			runTimeValues.put(varName, value);
		}
		else runTimeValues.put(varName,initValue(qlElement));
		
	}
	
	
	private Value initValue(SingleLineElement qlElement) {
		Type type = qlElement.getType();

		if (type.isCompatibleToBoolType()) {
			return new BoolValue(false);
		} else if (type.isCompatibleToIntType()) {
			return new IntValue(0);
		} else if (type.isCompatibleToMoneyType()) {
			return new DecValue(0.0f);
		} else {
			return new StrValue("");
		}

	}
	
	
	private List<JPanel> getQuestionPanelList(){
		return questionPanelList;
	}
	
	
	
}
