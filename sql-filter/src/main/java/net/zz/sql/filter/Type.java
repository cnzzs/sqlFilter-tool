package net.zz.sql.filter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/1.
 */
public enum  Type {
    i {
        public Object parse(String parse) {
           return Integer.parseInt(parse);
        }
    }, f {
        public Object parse(String parse) {
           return Float.parseFloat(parse);
        }
    }, d {
        public Object parse(String parse) {
           return Double.parseDouble(parse);
        }
   }, s {
        public Object parse(String parse) {
           return Short.parseShort(parse);
        }
    }, S {
        public Object parse(String parse) {
           return parse;
        }
   }, D {
        public Object parse(String parse) {
           return new Date(parse);
        }
  }, b {
        public Object parse(String parse) {
           return Boolean.parseBoolean(parse);
        }
  }, bd {
        public Object parse(String parse) {
           return new BigDecimal(parse);
        }
    };

    public abstract Object parse(String parse);
}
