package cn.hexing.ami.util.charts;

import java.util.List;
import java.util.Map;

public class AnnotationGroup {
	Map<String ,String> properties;
	List<Annotation> annotations;
	public AnnotationGroup(Map<String ,String> properties,List<Annotation> annotations){
		this.annotations=annotations;
		this.properties=properties;
	}
}
