package demo.elastic.search.feign.plus;

import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.po.response.InnerHits;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * scroll是加强版本
 */
@Service
public class ScrollServicePlus {

    @Resource
    private ScrollService scrollService;

    /**
     * 反序列化scroll的结果
     */
    public ESResponse _search(String scroll, String scroll_id) {
        String result = scrollService._search(scroll, scroll_id);
        return ESResponse.parse(result);
    }

    /**
     * 反序列化scroll的结果
     */
    public void _search(String scroll, String scroll_id, Consumer<InnerHits> consumer) {
        List<InnerHits> hits = new ArrayList<>();
        do {
            hits = this._search(scroll, scroll_id).getHits().getHits();
            hits.forEach(innerHits -> {
                consumer.accept(innerHits);
            });
        } while (hits.size() > 0);

    }
}
