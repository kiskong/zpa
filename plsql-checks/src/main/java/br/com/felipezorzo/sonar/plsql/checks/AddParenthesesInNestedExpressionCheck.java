/*
 * Sonar PL/SQL Plugin (Community)
 * Copyright (C) 2015 Felipe Zorzo
 * felipe.b.zorzo@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package br.com.felipezorzo.sonar.plsql.checks;

import java.util.List;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.ActivatedByDefault;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;

import com.sonar.sslr.api.AstNode;

import br.com.felipezorzo.sonar.plsql.api.PlSqlGrammar;

@Rule(
    key = AddParenthesesInNestedExpressionCheck.CHECK_KEY,
    priority = Priority.MAJOR
)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.READABILITY)
@SqaleConstantRemediation("2min")
@ActivatedByDefault
public class AddParenthesesInNestedExpressionCheck extends AbstractBaseCheck {

    public static final String CHECK_KEY = "AddParenthesesInNestedExpression";

    @Override
    public void init() {
        subscribeTo(PlSqlGrammar.OR_EXPRESSION);
    }

    @Override
    public void visitNode(AstNode node) {
        List<AstNode> andExpressions = node.getChildren(PlSqlGrammar.AND_EXPRESSION);
        for (AstNode andExpression : andExpressions) {
            getContext().createLineViolation(this, getLocalizedMessage(CHECK_KEY), andExpression);
        }
    }

}
