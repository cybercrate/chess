package com.wingmann.chess.util;

public enum Color {
    BLACK {
        @Override
        public String toString() {
            return "Black";
        }

        @Override
        public String toShortString() {
            return String.valueOf(toString().charAt(0));
        }
    },
    WHITE {
        @Override
        public String toString() {
            return "White";
        }

        @Override
        public String toShortString() {
            return String.valueOf(toString().charAt(0));
        }
    };

    public abstract String toShortString();

    public static Color invert(Color color) {
        return (color == BLACK) ? WHITE : BLACK;
    }
}
