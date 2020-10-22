package demo.elastic.search.out.etl.dao;

import demo.elastic.search.feign.plus.SearchServicePlus;
import demo.elastic.search.out.etl.vo.Tb0088Vo;
import demo.elastic.search.out.remove.compound.Body;
import demo.elastic.search.out.remove.compound.level.base.Term;
import demo.elastic.search.po.response.InnerHits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 这里使用ES作为
 */
@Slf4j
@Repository
public class DAO0088 {

    private static String index = "tb_object_0088";


    @Autowired
    private SearchServicePlus searchServicePlus;

    /**
     * 查询0088 根据F1_0088
     *
     * @param F6_0088
     * @return
     */
    public List<Tb0088Vo> query0088ByF6(String F6_0088) {
        List<Tb0088Vo> tb0088Vos = new ArrayList<>();
        Body body = Body.build(0, 1000);
        body.getQuery().getBool().getMust().getTerm().add(new Term("F6_0088", F6_0088));
        searchServicePlus._searchToConsumer(index, body.parse(), new Function<InnerHits, Boolean>() {
            @Override
            public Boolean apply(InnerHits innerHits) {
                String F1_0088 = innerHits.getSource().get("F1_0088") == null ? " " : innerHits.getSource().get("F1_0088").toString();
                String F2_0088 = innerHits.getSource().get("F2_0088") == null ? " " : innerHits.getSource().get("F2_0088").toString();
                String F3_0088 = innerHits.getSource().get("F3_0088") == null ? " " : innerHits.getSource().get("F3_0088").toString();
                String F4_0088 = innerHits.getSource().get("F4_0088") == null ? " " : innerHits.getSource().get("F4_0088").toString();
                String F5_0088 = innerHits.getSource().get("F5_0088") == null ? " " : innerHits.getSource().get("F5_0088").toString();
                String F6_0088 = innerHits.getSource().get("F6_0088") == null ? " " : innerHits.getSource().get("F6_0088").toString();
                String F7_0088 = innerHits.getSource().get("F7_0088") == null ? " " : innerHits.getSource().get("F7_0088").toString();
                String F8_0088 = innerHits.getSource().get("F8_0088") == null ? " " : innerHits.getSource().get("F8_0088").toString();
                String F9_0088 = innerHits.getSource().get("F9_0088") == null ? " " : innerHits.getSource().get("F9_0088").toString();
                String F10_0088 = innerHits.getSource().get("F10_0088") == null ? " " : innerHits.getSource().get("F10_0088").toString();
                String F11_0088 = innerHits.getSource().get("F11_0088") == null ? " " : innerHits.getSource().get("F11_0088").toString();
                String F12_0088 = innerHits.getSource().get("F12_0088") == null ? " " : innerHits.getSource().get("F12_0088").toString();
                String F13_0088 = innerHits.getSource().get("F13_0088") == null ? " " : innerHits.getSource().get("F13_0088").toString();
                String F14_0088 = innerHits.getSource().get("F14_0088") == null ? " " : innerHits.getSource().get("F14_0088").toString();
                String F15_0088 = innerHits.getSource().get("F15_0088") == null ? " " : innerHits.getSource().get("F15_0088").toString();
                String F16_0088 = innerHits.getSource().get("F16_0088") == null ? " " : innerHits.getSource().get("F16_0088").toString();
                String F17_0088 = innerHits.getSource().get("F17_0088") == null ? " " : innerHits.getSource().get("F17_0088").toString();
                String F18_0088 = innerHits.getSource().get("F18_0088") == null ? " " : innerHits.getSource().get("F18_0088").toString();
                String F19_0088 = innerHits.getSource().get("F19_0088") == null ? " " : innerHits.getSource().get("F19_0088").toString();
                String F20_0088 = innerHits.getSource().get("F20_0088") == null ? " " : innerHits.getSource().get("F20_0088").toString();
                String F21_0088 = innerHits.getSource().get("F21_0088") == null ? " " : innerHits.getSource().get("F21_0088").toString();
                String F22_0088 = innerHits.getSource().get("F22_0088") == null ? " " : innerHits.getSource().get("F22_0088").toString();
                String F23_0088 = innerHits.getSource().get("F23_0088") == null ? " " : innerHits.getSource().get("F23_0088").toString();
                String F24_0088 = innerHits.getSource().get("F24_0088") == null ? " " : innerHits.getSource().get("F24_0088").toString();
                String F25_0088 = innerHits.getSource().get("F25_0088") == null ? " " : innerHits.getSource().get("F25_0088").toString();
                String F26_0088 = innerHits.getSource().get("F26_0088") == null ? " " : innerHits.getSource().get("F26_0088").toString();
                Tb0088Vo tb0088Vo = new Tb0088Vo(
                        F1_0088, F2_0088, F3_0088, F4_0088, F5_0088, F6_0088, F7_0088, F8_0088, F9_0088, F10_0088,
                        F11_0088, F12_0088, F13_0088, F14_0088, F15_0088, F16_0088, F17_0088, F18_0088, F19_0088, F20_0088,
                        F21_0088, F22_0088, F23_0088, F24_0088, F25_0088, F26_0088);
                tb0088Vos.add(tb0088Vo);
                return true;
            }
        });
        return tb0088Vos;
    }
}
