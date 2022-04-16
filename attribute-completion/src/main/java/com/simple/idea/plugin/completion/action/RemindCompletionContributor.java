package com.simple.idea.plugin.completion.action;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.MinusculeMatcher;
import com.intellij.psi.codeStyle.NameUtil;
import com.intellij.util.ProcessingContext;
import com.simple.idea.plugin.completion.application.IWordManageService;
import com.simple.idea.plugin.completion.domain.model.Node;
import com.simple.idea.plugin.completion.domain.service.WordManageServiceImpl;
import com.simple.idea.plugin.completion.infrastructure.PluginUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.intellij.patterns.PlatformPatterns.psiElement;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2022-04-16 12:27
 **/
public class RemindCompletionContributor extends CompletionContributor {
    public RemindCompletionContributor() {
        IWordManageService wordManageService = ApplicationManager.getApplication().getService(WordManageServiceImpl.class);
        //
        CompletionProvider<CompletionParameters> provider = new DefaultCompletionProvider(wordManageService);

        extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiLocalVariable.class), provider);
        extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiMethod.class), provider);
        extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiField.class), provider);
        extend(CompletionType.BASIC, psiElement(PsiIdentifier.class).withParent(PsiParameter.class), provider);
    }

    static class DefaultCompletionProvider extends CompletionProvider<CompletionParameters> {

        private final IWordManageService wordManageService;

        public DefaultCompletionProvider(IWordManageService wordManageService) {
            this.wordManageService = wordManageService;
        }

        /**
         * 当我们输入字母的时候，会触发流程走到这里，然后我们的规则是第一个字母开始
         *
         * @param parameters 这里是包含：当前输入位置，也就是光标的坐标信息；然后整个文件信息等；
         * @param context
         * @param result 这里是包含了我们的匹配信息"a"等字母；
         */
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            // 获取到匹配的前缀
            PrefixMatcher prefixMatcher = result.getPrefixMatcher();
            String prefix = prefixMatcher.getPrefix();

            if (PluginUtils.isCompositeName(prefix)) {
                prefix = PluginUtils.getLastWord(prefix).toLowerCase(Locale.ROOT);
            }
            List<Node> list = wordManageService.searchPrefix(prefix);
            result.restartCompletionOnAnyPrefixChange();
            if (list.isEmpty()) {
                result.restartCompletionOnAnyPrefixChange();
                return;
            }

            List<LookupElementBuilder> collect = list.stream().map(node -> LookupElementBuilder
                    .create(node.word)
                    .withPresentableText(node.word)
                    .withIcon(AllIcons.Actions.More)
                    .withBoldness(false)
                    .withTypeText(node.explain)
                    .bold()
                    .withInsertHandler((ctx, item) -> {
                        String text = ctx.getDocument().getText(new TextRange(ctx.getStartOffset() - 1, ctx.getStartOffset()));
                        if (StringUtil.isEmpty(text)) {
                            return;
                        }
                        char symbol = text.charAt(0);
                        String insertText = item.getLookupString();
                        if (Character.isLowerCase(symbol)) {
                            insertText = StringUtil.capitalize(item.getLookupString());
                        }
                        ctx.getDocument().replaceString(ctx.getStartOffset(), ctx.getTailOffset(), insertText);
                    }))
                    .collect(Collectors.toList());

            CompletionResultSet resultSet = result.withPrefixMatcher(new MyPrefixMatcher(prefix));
            resultSet.addAllElements(collect);
            resultSet.addLookupAdvertisement("匹配数量" + list.size());
        }
    }

    static class MyPrefixMatcher extends PrefixMatcher {

        private final MinusculeMatcher matcher;

        public MyPrefixMatcher(String prefix) {
            super(prefix);
            matcher = NameUtil.buildMatcher(CamelHumpMatcher.applyMiddleMatching(getPrefix())).typoTolerant().build();
        }

        @Override
        public boolean prefixMatches(@NotNull String name) {
            return matcher.isStartMatch(name);
        }

        @Override
        public int matchingDegree(String string) {
            return string.startsWith(getPrefix()) ? 1 : 0;
        }

        @Override
        public @NotNull
        PrefixMatcher cloneWithPrefix(@NotNull String prefix) {
            return new MyPrefixMatcher(prefix);
        }

    }
}
