package com.wingmann.chess.piece;

public enum ID {
    KING {
        @Override
        public String toString() {
            return "K";
        }

        @Override
        public String toFullString() {
            return "King";
        }
    },
    QUEEN {
        @Override
        public String toString() {
            return "Q";
        }

        @Override
        public String toFullString() {
            return "Queen";
        }
    },
    ROOK {
        @Override
        public String toString() {
            return "R";
        }

        @Override
        public String toFullString() {
            return "Rook";
        }
    },
    BISHOP {
        @Override
        public String toString() {
            return "B";
        }

        @Override
        public String toFullString() {
            return "Bishop";
        }
    },
    KNIGHT {
        @Override
        public String toString() {
            return "N";
        }

        @Override
        public String toFullString() {
            return "Knight";
        }
    },
    PAWN {
        @Override
        public String toString() {
            return "P";
        }

        @Override
        public String toFullString() {
            return "Pawn";
        }
    };

    public abstract String toFullString();
}
