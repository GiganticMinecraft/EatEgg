package click.seichi.eategg.commands;

public final class PrintExecutor extends Executor {
  public PrintExecutor(String commandName) {
    super(commandName);
  }

  @Override
  public void execute(CommandContext context) {
    context.sender().sendMessage(context.command().getUsage());
  }
}
