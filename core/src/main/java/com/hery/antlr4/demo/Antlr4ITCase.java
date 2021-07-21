package com.hery.antlr4.demo;

import com.hery.antlr4.parse.MathLexer;
import com.hery.antlr4.parse.MathParser;
import com.hery.antlr4.parse.MathVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * @ClassName Antlr4ITCase
 * @Description TODO
 * @Date 2021/7/13 20:02
 * @Author yongheng
 * @Version V1.0
 **/
public class Antlr4ITCase {
    public static void main(String[] args) {
        //将字符串转换为响应的字符流程
        CharStream input = CharStreams.fromString("12*2+12\r\n");
        // 生成词法解析树
        MathLexer lexer = new MathLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // 将token 流转换成语法分析器
        MathParser parser = new MathParser(tokens);
        // stat  这个方法，是因为该 g4文件里定义了该命名
//        MathParser.StatContext tree1 = parser.stat();


        ParseTree tree = parser.prog(); // parse

//        MathVisitor vt=new MathVisitor();
//        vt.visit(tree);
        System.out.println(tree.toStringTree(parser));
    }
}
