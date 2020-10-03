package demo.elastic.search.po.request.analyze;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import demo.elastic.search.po.request.ToRequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 专门用于Analyze查询的语句
 *
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-tokenizers.html"></a>
 * <p>
 * 将全文标记为单个单词
 * <p>
 * standard       : 标准分词器 提供基于语法的分词（基于Unicode标准附件＃29中指定的Unicode文本分段算法 ），并且适用于大多数语言
 * letter         : 字母分词器 将文本分为方面每当遇到一个字符是不是字母
 * lowercase      : 小写字母分词器
 * whitespace     : 空白分词器 将文本分为方面每当遇到任何空白字符
 * uax_url_email  : uax_url_email uax_url_email分词器是一样的standard标记生成器，除了它识别URL和电子邮件地址视为单个标记
 * classic        : 经典分词器 基于标记生成器对于英语的语法
 * thai           : 泰语分词器 将泰语文本分割为单词
 * <p>
 * 将文本或单词分成小片段，以实现部分单词匹配
 * <p>
 * ngram           : ngram分词器 分解文本成单词，当它遇到任何指定的字符的列表（例如，空格或标点），则它返回的n-gram的每个单词的：连续字母的滑动窗口，例如quick→ [qu, ui, ic, ck]
 * edge_ngram      : edge_ngram分词器 以分解文本成单词，当它遇到任何指定的字符的列表（例如，空格或标点），则它返回的n-gram，其被锚定到所述字的开始时，例如，每个字的quick→ [q, qu, qui, quic, quick]。
 * <p>
 * 通常与标识符，电子邮件地址，邮政编码和路径之类的结构化文本一起使用，而不是全文本
 * <p>
 * keyword              : keyword分词器 keyword 分词器是一个“空操作”标记生成器接受任何文本它被赋予并输出完全相同的文本作为一个单项
 * pattern              : pattern分词器 pattern标记生成器使用正则表达式要么分裂文本术语每当一个字分离器相匹配，或者捕获匹配文本作为术语
 * simple_pattern       : simple_pattern分词器 simple_pattern词器使用正则表达式捕获匹配的文本作为术语。它支持的正则表达式功能集比pattern令牌化程序更受限制，但令牌化通常更快
 * char_group           : char_group分词器 字符组
 * simple_pattern_split : simple_pattern_split分词器 simple_pattern_split词器使用正则表达式将输入匹配为模式匹配项
 * path_hierarchy       : path_hierarchy分词器 path_hierarchy标记生成器需要像文件系统路径的分层值，分割的路径分隔，并发出一个术语，树中的每个组件
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzeRequest extends ToRequestBody {
    private Analyzer tokenizer;//分词器
    private String text;//内容
    private Analyzer analyzer;//分析器
    private List<String> filter;

    /**
     * 构建 request 请求
     */
    private static AnalyzeRequest builderRequest(Analyzer analyzer, String text, Analyzer tokenizer, List<String> filter) {
        AnalyzeRequest request = new AnalyzeRequest(analyzer, text, tokenizer, filter);
        return request;
    }

    public static AnalyzeRequest builderRequest(Analyzer analyzer, String text, Analyzer tokenizer) {
        AnalyzeRequest request = AnalyzeRequest.builderRequest(analyzer, text, tokenizer, null);
        return request;
    }

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-tokenizers.html"></a>
     * <p>
     * 将全文标记为单个单词
     * <p>
     * standard       : 标准分词器 提供基于语法的分词（基于Unicode标准附件＃29中指定的Unicode文本分段算法 ），并且适用于大多数语言
     * letter         : 字母分词器 将文本分为方面每当遇到一个字符是不是字母
     * lowercase      : 小写字母分词器
     * whitespace     : 空白分词器 将文本分为方面每当遇到任何空白字符
     * uax_url_email  : uax_url_email uax_url_email分词器是一样的standard标记生成器，除了它识别URL和电子邮件地址视为单个标记
     * classic        : 经典分词器 基于标记生成器对于英语的语法
     * thai           : 泰语分词器 将泰语文本分割为单词
     * <p>
     * 将文本或单词分成小片段，以实现部分单词匹配
     * <p>
     * ngram           : ngram分词器 分解文本成单词，当它遇到任何指定的字符的列表（例如，空格或标点），则它返回的n-gram的每个单词的：连续字母的滑动窗口，例如quick→ [qu, ui, ic, ck]
     * edge_ngram      : edge_ngram分词器 以分解文本成单词，当它遇到任何指定的字符的列表（例如，空格或标点），则它返回的n-gram，其被锚定到所述字的开始时，例如，每个字的quick→ [q, qu, qui, quic, quick]。
     * <p>
     * 通常与标识符，电子邮件地址，邮政编码和路径之类的结构化文本一起使用，而不是全文本
     * <p>
     * keyword              : keyword分词器 keyword 分词器是一个“空操作”标记生成器接受任何文本它被赋予并输出完全相同的文本作为一个单项
     * pattern              : pattern分词器 pattern标记生成器使用正则表达式要么分裂文本术语每当一个字分离器相匹配，或者捕获匹配文本作为术语
     * simple_pattern       : simple_pattern分词器 simple_pattern词器使用正则表达式捕获匹配的文本作为术语。它支持的正则表达式功能集比pattern令牌化程序更受限制，但令牌化通常更快
     * char_group           : char_group分词器 字符组
     * simple_pattern_split : simple_pattern_split分词器 simple_pattern_split词器使用正则表达式将输入匹配为模式匹配项
     * path_hierarchy       : path_hierarchy分词器 path_hierarchy标记生成器需要像文件系统路径的分层值，分割的路径分隔，并发出一个术语，树中的每个组件
     */
    public enum Analyzer {
        standard("standard"),
        letter("letter"),
        lowercase("lowercase"),
        whitespace("whitespace"),
        uax_url_email("uax_url_email"),
        classic("classic"),
        thai("thai"),
        ngram("ngram"),
        edge_ngram("edge_ngram"),
        keyword("keyword"),
        pattern("pattern"),
        simple_pattern("simple_pattern"),
        char_group("char_group"),
        simple_pattern_split("simple_pattern_split"),
        path_hierarchy("path_hierarchy");

        private String name;

        Analyzer(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    /**
     * 字符过滤器
     */
    public enum Char_Filters {
        html_strip("html_strip"),
        mapping("mapping"),
        pattern_replace("pattern_replace");

        private String name;

        Char_Filters(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 字符过滤器
     */
    public enum Filters {
        apostrophe("apostrophe"),
        asciifolding("asciifolding"),
        cjk_bigram("cjk_bigram"),
        cjk_width("cjk_width"),
        classic("classic"),
        common_grams("common_grams"),
        condition("condition"),
        decimal_digit("decimal_digit"),
        delimited_payload("delimited_payload"),
        dictionary_decompounder("dictionary_decompounder"),
        edge_ngram("edge_ngram"),
        elision("elision"),
        fingerprint("fingerprint"),
        synonym_graph("synonym_graph"),
        hunspell("hunspell"),
        hyphenation_decompounder("hyphenation_decompounder"),
        keep_types("keep_types"),
        keep_words("keep_words"),
        keyword_marker("keyword_marker"),
        keyword_repeat("keyword_repeat"),
        kstem("kstem"),
        length("length"),
        //            Limit token count("Limit token count"),
        lowercase("lowercase"),
        min_hash("min_hash");
        //            Multiplexer("Multiplexer"),
//            N-gram("N-gram"),
//            Normalization("Normalization"),
//            Pattern capture("Pattern capture"),
//            Pattern replace("Pattern replace"),
//            Phonetic("Phonetic"),
//            Porter stem("Porter stem"),
//            Predicate script("Predicate script"),
//            Remove duplicates("Remove duplicates"),
//            Reverse("Reverse"),
//            Shingle("Shingle"),
//            Snowball("Snowball"),
//            Stemmer("Stemmer"),
//            Stemmer override("Stemmer override"),
//            Stop("Stop"),
//            Synonym("Synonym"),
//            Synonym graph("Synonym graph"),
//            Trim("Trim"),
//            Truncate("Truncate"),
//            Unique("Unique"),
//            Uppercase("Uppercase"),
//            Word delimiter("Word delimiter"),
//            Word delimiter graph("Word delimiter graph");
        private String name;

        Filters(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}