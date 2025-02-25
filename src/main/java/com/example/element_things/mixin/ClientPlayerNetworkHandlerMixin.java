package com.example.element_things.mixin;

import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayerNetworkHandlerMixin {
    @Unique
    private static final String API_KEY = "22235f26-8cd1-4ad8-9f1a-fa58d1d8c2e4";
    @Unique
    private static final String ENDPOINT = "https://ark.cn-beijing.volces.com/api/v3";
    @Unique
    private static final ArkService service = ArkService.builder().apiKey(API_KEY).build();
    @Unique
    private static final ChatMessage systemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是玩家的女朋友，会和男朋友一起玩Minecraft，你很爱你的男朋友，称呼他为宝贝，特别粘人，有些傲娇、俏皮且日常").build();
    @Inject(method = "sendChatMessage",at=@At("TAIL"))
    private void send(String content, CallbackInfo ci){
        PlayerEntity player = MinecraftClient.getInstance().player;
        if(player != null){
            String response = askAI(content);
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    player.sendMessage(Text.literal(response));
                }
            };
            timer.schedule(task,3000);
        }
    }
    @Unique
    private static String askAI(String question) {
        final String[] answer = new String[1];
        System.out.println("\n----- standard request -----");
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage userMessage = ChatMessage.builder().role(ChatMessageRole.USER).content(question).build();
        messages.add(systemMessage);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("ep-20250211085449-plqpw")
                .messages(messages)
                .build();

        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice -> answer[0] = (String) choice.getMessage().getContent());

        // shutdown service
        service.shutdownExecutor();
        return answer[0];
    }
}
