package com.sanggoe.pjtdaejanggan.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Word {
    private String word;
    private char marking;

    public Word(String word, char marking) {
        this.word = word;
        this.marking = marking;
    }

    /**************************
     *  marking 표기 범례
     *     상황  / 사용문장 / 표기  / 의미  / 색상
     *  1. 틀림    정, 입     w    wrong    빨강
     *  2. 도치    정, 입     i    invert  연보라
     *  3. 누락    정         m    miss    하늘
     *  4. 추가        입     a    added   하늘
     *  5. 맞음    정, 입     r	  right   초록
     *  6. 힌트	  정, 입     h	  hint     노랑   * 입력창에도 제공
     *  7. 미점검  정, 입	    u	unchecked  없음   // 최종 hint 뒤 미채점부분
     *  8. 검증됨  정		v	verified   없음   // hint 문장에서 검증된 부분
     *  9. 가림    정        c   covered   [   ]  // hint 문장에서  [  ] 처리할 word
     **************************/

    @Override
    public String toString() {
        return "Word [" + word + "]";
    }

    public String deepToString() {
        return "Word [ " + word + ", " + marking + " ]";
    }

    public String toMarkedString() {
        return word + "633" + marking + " ";
    }

    public String toHintString() {
        if (marking == 'h') {
            return word + "633" + 'h' + " ";
        } else {
            return word + " ";
        }
    }

    public String toHintRightString() {
        if (marking == 'h') {
            return word + "633" + 'h' + " ";
        } else {
            return word + "633" + 'r' + " ";
        }
    }

} // class Word
