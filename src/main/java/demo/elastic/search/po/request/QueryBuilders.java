package demo.elastic.search.po.request;

import demo.elastic.search.po.request.dsl.compound.BoolQuery;
import demo.elastic.search.po.request.dsl.full.*;
import demo.elastic.search.po.request.dsl.matchall.MatchAllQuery;
import demo.elastic.search.po.request.dsl.term.*;
import demo.elastic.search.po.request.lucene.LuceneQueryStringQuery;
import demo.elastic.search.po.request.lucene.LuceneSimpleQueryStringQuery;

import java.util.List;

/**
 * Utility class to create search queries.
 */
public final class QueryBuilders {

    private QueryBuilders() {
    }

    /**
     * A query that matches on all documents.
     */
    public static MatchAllQuery matchAllQuery() {
        return MatchAllQuery.builderQuery();
    }

    public static MatchQuery matchQuery(String field, String query) {
        return MatchQuery.builderQuery(field, query);
    }

    public static MultiMatchQuery multiMatchQuery(List<String> fields, String query) {
        return MultiMatchQuery.builderQuery(fields, query);
    }

    public static MatchPhraseQuery matchPhraseQuery(String field, String query) {
        return MatchPhraseQuery.builderQuery(field, query);
    }

    public static MatchPhrasePrefixQuery matchPhrasePrefixQuery(String field, String query) {
        return MatchPhrasePrefixQuery.builderQuery(field, query);
    }

    public static MatchBoolPrefixQuery matchBoolPrefixQuery(String field, String query) {
        return MatchBoolPrefixQuery.builderQuery(field, query);
    }

    public static IDsQuery idsQuery(List<String> values) {
        return IDsQuery.builderQuery(values);
    }


    public static TermQuery termQuery(String name, String value) {
        return TermQuery.builderQuery(name, value);
    }

    public static FuzzyQuery fuzzyQuery(String key, String value) {
        return FuzzyQuery.builderQuery(key, value, "AUTO", 50, 0, true, "constant_score");
    }

    public static FuzzyQuery fuzzyQuery(String key, String value, String fuzziness) {
        return FuzzyQuery.builderQuery(key, value, fuzziness, 50, 0, true, "constant_score");
    }

    public static PrefixQuery prefixQuery(String name, String prefix) {
        return PrefixQuery.builderQuery(name, prefix);
    }

    public static RangeQuery rangeQuery(String key, String gte, String gt, String lte, String lt, Double boost) {
        return RangeQuery.builderQuery(key, gte, gt, lte, lt, boost);
    }

    public static RangeQuery rangeQuery(String key, String gte, String gt, String lte, String lt) {
        return RangeQuery.builderQuery(key, gte, gt, lte, lt, 1.0);
    }

    public static RangeQuery rangeQuery(String key, String gte, String lte) {
        return rangeQuery(key, gte, null, lte, null, 1.0);
    }

    public static RangeQuery rangeGteQuery(String key, String gte) {
        return rangeQuery(key, gte, null, null, null, 1.0);
    }

    public static RangeQuery rangeLteQuery(String key, String lte) {
        return rangeQuery(key, null, null, lte, null, 1.0);
    }

    public static WildcardQuery wildcardQuery(String name, String query) {
        return WildcardQuery.builderQuery(name, query);
    }

    public static TermsQuery termsQuery(String name, List<String> values) {
        return TermsQuery.builderQuery(name, values);
    }

    /**
     * 正则
     */
    public static RegexpQuery regexpQuery(String name, String regexp) {
        return RegexpQuery.builderQuery(name, regexp);
    }


    public static LuceneQueryStringQuery queryStringQuery(String queryString) {
        return LuceneQueryStringQuery.builderQuery(queryString);
    }

    public static LuceneSimpleQueryStringQuery simpleQueryStringQuery(String queryString) {
        return LuceneSimpleQueryStringQuery.builderQuery(queryString);
    }

    public static ExistsQuery existsQuery(String name) {
        return ExistsQuery.builderQuery(name);
    }

    /**
     * A Query that matches documents matching boolean combinations of other queries.
     */
    public static BoolQuery boolQuery() {
        return new BoolQuery();
    }

//    public static BoostingQueryBuilder boostingQuery(QueryBuilder positiveQuery, QueryBuilder negativeQuery) {
//        return new BoostingQueryBuilder(positiveQuery, negativeQuery);
//    }


//
//    public static SpanTermQueryBuilder spanTermQuery(String name, String value) {
//        return new SpanTermQueryBuilder(name, value);
//    }
//
//    public static SpanTermQueryBuilder spanTermQuery(String name, int value) {
//        return new SpanTermQueryBuilder(name, value);
//    }
//
//    public static SpanTermQueryBuilder spanTermQuery(String name, long value) {
//        return new SpanTermQueryBuilder(name, value);
//    }
//
//    public static SpanTermQueryBuilder spanTermQuery(String name, float value) {
//        return new SpanTermQueryBuilder(name, value);
//    }
//
//    public static SpanTermQueryBuilder spanTermQuery(String name, double value) {
//        return new SpanTermQueryBuilder(name, value);
//    }
//
//    public static SpanFirstQueryBuilder spanFirstQuery(SpanQueryBuilder match, int end) {
//        return new SpanFirstQueryBuilder(match, end);
//    }
//
//    public static SpanNearQueryBuilder spanNearQuery(SpanQueryBuilder initialClause, int slop) {
//        return new SpanNearQueryBuilder(initialClause, slop);
//    }
//
//    public static SpanNotQueryBuilder spanNotQuery(SpanQueryBuilder include, SpanQueryBuilder exclude) {
//        return new SpanNotQueryBuilder(include, exclude);
//    }
//
//    public static SpanOrQueryBuilder spanOrQuery(SpanQueryBuilder initialClause) {
//        return new SpanOrQueryBuilder(initialClause);
//    }

//    /**
//     * Creates a new {@code span_within} builder.
//     *
//     * @param big    the big clause, it must enclose {@code little} for a match.
//     * @param little the little clause, it must be contained within {@code big} for a match.
//     */
//    public static SpanWithinQueryBuilder spanWithinQuery(SpanQueryBuilder big, SpanQueryBuilder little) {
//     *         return new SpanWithinQueryBuilder(big, little);
//     *     }
//     *
//     *     /**
//     *      * Creates a new {@code span_containing} builder.
//     *      *
//     *      * @param big    the big clause, it must enclose {@code little} for a match.
//     *      * @param little the little clause, it must be contained within {@code big} for a match.
//     *      */
//     *
//
//    public static SpanContainingQueryBuilder spanContainingQuery(SpanQueryBuilder big, SpanQueryBuilder little) {
//     *return new SpanContainingQueryBuilder(big, little);
//     *}
//     *
//             *     /**
//      *      * Creates a {@link SpanQueryBuilder} which allows having a sub query
//      *      * which implements {@link MultiTermQueryBuilder}. This is useful for
//      *      * having e.g. wildcard or fuzzy queries inside spans.
//      *      *
//      *      * @param multiTermQueryBuilder The {@link MultiTermQueryBuilder} that
//      *      *                              backs the created builder.
//      *      */
//             *
//             *
//
//    public static SpanMultiTermQueryBuilder spanMultiTermQueryBuilder(MultiTermQueryBuilder multiTermQueryBuilder) {
//     *return new SpanMultiTermQueryBuilder(multiTermQueryBuilder);
//     *}
//     *
//             *
//
//    public static FieldMaskingSpanQueryBuilder fieldMaskingSpanQuery(SpanQueryBuilder query, String field) {
//     *return new FieldMaskingSpanQueryBuilder(query, field);
//     *}
//     *
//             *     /**
//      *      * A query that wraps another query and simply returns a constant score equal to the
//      *      * query boost for every document in the query.
//      *      *
//      *      * @param queryBuilder The query to wrap in a constant score query
//      *      */
//             *
//
//    public static ConstantScoreQueryBuilder constantScoreQuery(QueryBuilder queryBuilder) {
//     *return new ConstantScoreQueryBuilder(queryBuilder);
//     *}
//     *
//             *     /**
//      *      * A function_score query with no functions.
//      *      *
//      *      * @param queryBuilder The query to custom score
//      *      * @return the function score query
//      *      */
//             *
//
//    public static FunctionScoreQueryBuilder functionScoreQuery(QueryBuilder queryBuilder) {
//     *return new FunctionScoreQueryBuilder(queryBuilder);
//     *}
//     *
//             *     /**
//      *      * A query that allows to define a custom scoring function
//      *      *
//      *      * @param queryBuilder           The query to custom score
//      *      * @param filterFunctionBuilders the filters and functions to execute
//      *      * @return the function score query
//      *      */
//             *
//
//    public static FunctionScoreQueryBuilder functionScoreQuery(QueryBuilder queryBuilder,
//     *FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders) {
//     *return new FunctionScoreQueryBuilder(queryBuilder, filterFunctionBuilders);
//     *}
//     *
//             *     /**
//      *      * A query that allows to define a custom scoring function
//      *      *
//      *      * @param filterFunctionBuilders the filters and functions to execute
//      *      * @return the function score query
//      *      */
//             *
//
//    public static FunctionScoreQueryBuilder functionScoreQuery(FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders) {
//     *return new FunctionScoreQueryBuilder(filterFunctionBuilders);
//     *}
//     *
//             *     /**
//      *      * A query that allows to define a custom scoring function.
//      *      *
//      *      * @param function The function builder used to custom score
//      *      */
//             *
//
//    public static FunctionScoreQueryBuilder functionScoreQuery(ScoreFunctionBuilder function) {
//     *return new FunctionScoreQueryBuilder(function);
//     *}
//     *
//             *     /**
//      *      * A query that allows to define a custom scoring function.
//      *      *
//      *      * @param queryBuilder The query to custom score
//      *      * @param function     The function builder used to custom score
//      *      */
//             *
//
//    public static FunctionScoreQueryBuilder functionScoreQuery(QueryBuilder queryBuilder, ScoreFunctionBuilder function) {
//     *return (new FunctionScoreQueryBuilder(queryBuilder, function));
//     *}
//     *
//             *     /**
//      *      * A query that allows to define a custom scoring function through script.
//      *      *
//      *      * @param queryBuilder The query to custom score
//      *      * @param script       The script used to score the query
//      *      */
//             *
//
//    public static ScriptScoreQueryBuilder scriptScoreQuery(QueryBuilder queryBuilder, Script script) {
//     *return new ScriptScoreQueryBuilder(queryBuilder, script);
//     *}
//     *
//             *
//             *     /**
//      *      * A more like this query that finds documents that are "like" the provided texts or documents
//      *      * which is checked against the fields the query is constructed with.
//      *      *
//      *      * @param fields    the field names that will be used when generating the 'More Like This' query.
//      *      * @param likeTexts the text to use when generating the 'More Like This' query.
//      *      * @param likeItems the documents to use when generating the 'More Like This' query.
//      *      */
//             *
//
//    public static MoreLikeThisQueryBuilder moreLikeThisQuery(String[] fields, String[] likeTexts, Item[] likeItems) {
//     *return new MoreLikeThisQueryBuilder(fields, likeTexts, likeItems);
//     *}
//     *
//             *     /**
//      *      * A more like this query that finds documents that are "like" the provided texts or documents
//      *      * which is checked against the "_all" field.
//      *      *
//      *      * @param likeTexts the text to use when generating the 'More Like This' query.
//      *      * @param likeItems the documents to use when generating the 'More Like This' query.
//      *      */
//             *
//
//    public static MoreLikeThisQueryBuilder moreLikeThisQuery(String[] likeTexts, Item[] likeItems) {
//     *return moreLikeThisQuery(null, likeTexts, likeItems);
//     *}
//     *
//             *     /**
//      *      * A more like this query that finds documents that are "like" the provided texts
//      *      * which is checked against the "_all" field.
//      *      *
//      *      * @param likeTexts the text to use when generating the 'More Like This' query.
//      *      */
//             *
//
//    public static MoreLikeThisQueryBuilder moreLikeThisQuery(String[] likeTexts) {
//     *return moreLikeThisQuery(null, likeTexts, null);
//     *}
//     *
//             *     /**
//      *      * A more like this query that finds documents that are "like" the provided documents
//      *      * which is checked against the "_all" field.
//      *      *
//      *      * @param likeItems the documents to use when generating the 'More Like This' query.
//      *      */
//             *
//
//    public static MoreLikeThisQueryBuilder moreLikeThisQuery(Item[] likeItems) {
//     *return moreLikeThisQuery(null, null, likeItems);
//     *}
//     *
//             *
//
//    public static NestedQueryBuilder nestedQuery(String path, QueryBuilder query, ScoreMode scoreMode) {
//     *return new NestedQueryBuilder(path, query, scoreMode);
//     *}
//     *
//             *


//    /**
//     * A Query builder which allows building a query thanks to a JSON string or binary data.
//     */
//    public static WrapperQueryBuilder wrapperQuery(String source) {
//        return new WrapperQueryBuilder(source);
//    }
//
//    /**
//     * A Query builder which allows building a query thanks to a JSON string or binary data.
//     */
//    public static WrapperQueryBuilder wrapperQuery(BytesReference source) {
//        return new WrapperQueryBuilder(source);
//    }
//
//    /**
//     * A Query builder which allows building a query thanks to a JSON string or binary data.
//     */
//    public static WrapperQueryBuilder wrapperQuery(byte[] source) {
//        return new WrapperQueryBuilder(source);
//    }
//
//
//    /**
//     * A builder for filter based on a script.
//     *
//     * @param script The script to filter by.
//     */
//    public static ScriptQueryBuilder scriptQuery(Script script) {
//        return new ScriptQueryBuilder(script);
//    }
//
//
//    /**
//     * A filter to filter based on a specific distance from a specific geo location / point.
//     *
//     * @param name The location field name.
//     */
//    public static GeoDistanceQueryBuilder geoDistanceQuery(String name) {
//        return new GeoDistanceQueryBuilder(name);
//    }
//
//    /**
//     * A filter to filter based on a bounding box defined by top left and bottom right locations / points
//     *
//     * @param name The location field name.
//     */
//    public static GeoBoundingBoxQueryBuilder geoBoundingBoxQuery(String name) {
//        return new GeoBoundingBoxQueryBuilder(name);
//    }
//
//    /**
//     * A filter to filter based on a polygon defined by a set of locations  / points.
//     *
//     * @param name The location field name.
//     */
//    public static GeoPolygonQueryBuilder geoPolygonQuery(String name, List<GeoPoint> points) {
//        return new GeoPolygonQueryBuilder(name, points);
//    }
//
//    /**
//     * A filter based on the relationship of a shape and indexed shapes
//     *
//     * @param name  The shape field name
//     * @param shape Shape to use in the filter
//     */
//    public static GeoShapeQueryBuilder geoShapeQuery(String name, Geometry shape) throws IOException {
//        return new GeoShapeQueryBuilder(name, shape);
//    }
//
//
//    public static GeoShapeQueryBuilder geoShapeQuery(String name, String indexedShapeId) {
//        return new GeoShapeQueryBuilder(name, indexedShapeId);
//    }
//
//    /**
//     * A filter to filter indexed shapes intersecting with shapes
//     *
//     * @param name  The shape field name
//     * @param shape Shape to use in the filter
//     */
//    public static GeoShapeQueryBuilder geoIntersectionQuery(String name, Geometry shape) throws IOException {
//        GeoShapeQueryBuilder builder = geoShapeQuery(name, shape);
//        builder.relation(ShapeRelation.INTERSECTS);
//        return builder;
//    }
//
//
//    public static GeoShapeQueryBuilder geoIntersectionQuery(String name, String indexedShapeId) {
//        GeoShapeQueryBuilder builder = geoShapeQuery(name, indexedShapeId);
//        builder.relation(ShapeRelation.INTERSECTS);
//        return builder;
//    }
//
//
//    /**
//     * A filter to filter indexed shapes that are contained by a shape
//     *
//     * @param name  The shape field name
//     * @param shape Shape to use in the filter
//     */
//    public static GeoShapeQueryBuilder geoWithinQuery(String name, Geometry shape) throws IOException {
//        GeoShapeQueryBuilder builder = geoShapeQuery(name, shape);
//        builder.relation(ShapeRelation.WITHIN);
//        return builder;
//    }
//
//
//    public static GeoShapeQueryBuilder geoWithinQuery(String name, String indexedShapeId) {
//        GeoShapeQueryBuilder builder = geoShapeQuery(name, indexedShapeId);
//        builder.relation(ShapeRelation.WITHIN);
//        return builder;
//    }
//
//
//    /**
//     * A filter to filter indexed shapes that are not intersection with the query shape
//     *
//     * @param name  The shape field name
//     * @param shape Shape to use in the filter
//     */
//    public static GeoShapeQueryBuilder geoDisjointQuery(String name, Geometry shape) throws IOException {
//        GeoShapeQueryBuilder builder = geoShapeQuery(name, shape);
//        builder.relation(ShapeRelation.DISJOINT);
//        return builder;
//    }
//
//
//    public static GeoShapeQueryBuilder geoDisjointQuery(String name, String indexedShapeId) {
//        GeoShapeQueryBuilder builder = geoShapeQuery(name, indexedShapeId);
//        builder.relation(ShapeRelation.DISJOINT);
//        return builder;
//    }

//    /**
//     * A query that generates the union of documents produced by its sub-queries, and that scores each document
//     * with the maximum score for that document as produced by any sub-query, plus a tie breaking increment for any
//     * additional matching sub-queries.
//     */
//    public static DisMaxQueryBuilder disMaxQuery() {
//        return new DisMaxQueryBuilder();
//    }
//
//    /**
//     * A query to boost scores based on their proximity to the given origin for date, date_nanos and geo_point field types.
//     *
//     * @param name   The field name
//     * @param origin The origin of the distance calculation. Can be a long, string or {@link GeoPoint}, depending on field type.
//     * @param pivot  The distance from the origin at which relevance scores receive half of the boost value.
//     */
//    public static DistanceFeatureQueryBuilder distanceFeatureQuery(String name, Origin origin, String pivot) {
//        return new DistanceFeatureQueryBuilder(name, origin, pivot);
//    }

}
