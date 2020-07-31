package demo.elastic.search.po.request.dsl.compound;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import demo.elastic.search.po.request.dsl.term.RegexpRequest;
import demo.elastic.search.po.request.dsl.term.TermRequest;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = TermRequest.TermQuery.class, name = "term"),
//        @JsonSubTypes.Type(value = RegexpRequest.RegexpQuery.class, name = "regexp")})
public interface DSLQuery {

}