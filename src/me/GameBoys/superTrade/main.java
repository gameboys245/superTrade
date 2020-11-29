package me.GameBoys.superTrade;

import java.util.Hashtable;
import java.util.function.Consumer;



import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener {
    Hashtable<String, String> shorHand = new Hashtable<String, String>();
    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);
        shorHand.put("Ebook", "enchanted_book");
        shorHand.put("Eapple", "enchanted_golden_apple");
        shorHand.put("Etable", "enchanting_table");
        shorHand.put("Bmush", "brown_mushroom");
        shorHand.put("MushStem", "mushroom_stem");
        shorHand.put("mushStew", "mushroom_stew");
        shorHand.put("Rmush", "red_mushroom");
        shorHand.put("BmushBK", "brown_mushroom_block");
        shorHand.put("RmushBK", "red_mushroom_block");
        shorHand.put("Pand", "polished_andesite");
        shorHand.put("mossCobble", "mossy_cobblestone");
    }

    @Override
    public void onDisable() {}


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("mktrade")) {
            if  (args.length == 5) {
                Player p = (Player) sender;
                p.getInventory().addItem();
                if (args[2].equalsIgnoreCase("for")) {
                    p.sendMessage("NICE JOB");
                }
            }
        }
        return false;
    }
    public boolean hasc(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        Location zz = new Location(loc.getWorld(), x, y, z-1);
        Location zzz = new Location(loc.getWorld(), x, y, z+1);
        Location xx = new Location(loc.getWorld(), x-1, y, z);
        Location xxx = new Location(loc.getWorld(), x+1, y, z);
        Location yy = new Location(loc.getWorld(), x, y-1, z);
        Location yyy = new Location(loc.getWorld(), x, y+1, z);
        if(zz.getBlock().getType() == Material.CHEST) {return true;}
        if(zzz.getBlock().getType() == Material.CHEST) {return true;}
        if(xx.getBlock().getType() == Material.CHEST) {return true;}
        if(xxx.getBlock().getType() == Material.CHEST) {return true;}
        if(yy.getBlock().getType() == Material.CHEST) {return true;}
        if(yyy.getBlock().getType() == Material.CHEST) {return true;}
        return false;
    }
    public Block getc(Location loc) {
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        Location zz = new Location(loc.getWorld(), x, y, z-1);
        Location zzz = new Location(loc.getWorld(), x, y, z+1);
        Location xx = new Location(loc.getWorld(), x-1, y, z);
        Location xxx = new Location(loc.getWorld(), x+1, y, z);
        Location yy = new Location(loc.getWorld(), x, y-1, z);
        Location yyy = new Location(loc.getWorld(), x, y+1, z);
        if(zz.getBlock().getType() == Material.CHEST) {return zz.getBlock();}
        if(zzz.getBlock().getType() == Material.CHEST) {return zzz.getBlock();}
        if(xx.getBlock().getType() == Material.CHEST) {return xx.getBlock();}
        if(xxx.getBlock().getType() == Material.CHEST) {return xxx.getBlock();}
        if(yy.getBlock().getType() == Material.CHEST) {return yy.getBlock();}
        if(yyy.getBlock().getType() == Material.CHEST) {return yyy.getBlock();}
        return loc.getBlock();
    }
    public boolean chkline(String line) {
        if (line.split(" ").length != 2) {return false;}
        int a = 0;
        try {
            a = Integer.parseInt(line.split(" ")[0]);
        }
        catch( NumberFormatException e){return false;}
        try {
            XMaterial.matchXMaterial(line.split(" ")[1]).get();
        }catch(java.util.NoSuchElementException e) {
            if (shorHand.get(line.split(" ")[1]) == null)
            return false;
        }
        return true;
    }

    public ItemStack getline(String line) {
        String rname = line.split(" ")[1];
        if (shorHand.get(rname) != null)
            rname = shorHand.get(rname);
        ItemStack r = XMaterial.matchXMaterial(rname).get().parseItem();
        r.setAmount(Integer.parseInt(line.split(" ")[0]));
        return r;
    }
    @EventHandler
    public void onSignClick(PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {

            Block block = event.getClickedBlock();
            if (block.getType() == Material.OAK_WALL_SIGN) {
                Sign sign = (Sign)event.getClickedBlock().getState();



                if(sign.getLine(0).equalsIgnoreCase("ፐ뢩f")) {XMaterial.fxp(player);}
                if(sign.getLine(0).equalsIgnoreCase("[trade]") && sign.getLine(1).toString().length() > 5 && sign.getLine(3).toString().length() > 5 && sign.getLine(2).toString().equals("for")) {
                    boolean isok = false;
                    if (chkline(sign.getLine(1).toString()) && chkline(sign.getLine(3).toString()) && hasc(block.getLocation())){
                        player.sendMessage(ChatColor.GREEN + "trade chest created");
                        sign.setLine(0, ChatColor.GOLD+sign.getLine(0).toString());
                        sign.update();
                    }
                }else {
                    if(sign.getLine(0).equalsIgnoreCase(ChatColor.GOLD+"[trade]") & hasc(block.getLocation())){
                        ItemStack[] it = {null, null};
                        ItemStack[] un;
                        try {
                            un = new ItemStack[]{getline(sign.getLine(1)), getline(sign.getLine(3))};
                            }
                        catch(Exception e){return;}
                        final boolean[] cb = {false};
                        player.getInventory().forEach(new Consumer<ItemStack>() {

                            @Override
                            public void accept(ItemStack t) {
                                try {


                                    if (t.getType() == un[0].getType()) {
                                        if(t.getAmount() >= un[0].getAmount()) {
                                            cb[0] = true;
                                            it[0] = t;
                                        }
                                    }
                                }catch(java.lang.NullPointerException e) {return;}
                            }});

                        if (cb[0] == false) {player.sendMessage(ChatColor.DARK_RED+"You don't have what you need");}
                        else {


                            //un[0] = getline(sign.getLine(1));
                            cb[0] = false;
                            Chest chest = (Chest) getc(block.getLocation()).getState();
                            chest.getInventory().forEach(new Consumer<ItemStack>() {

                                @Override
                                public void accept(ItemStack t) {
                                    try {


                                        if (t.getType() == un[1].getType()) {
                                            if(t.getAmount() >= un[1].getAmount()) {
                                                cb[0] = true;
                                                it[1] = t;


                                            }
                                        }
                                    }catch(java.lang.NullPointerException e) {return;}
                                }});
                            if (cb[0] == false) {player.sendMessage(ChatColor.DARK_RED+"The shop is out of stock (cheapskate)");}
                            else {
                                //player.sendMessage("you can buy");

                                if (player.getInventory().firstEmpty() != -1 && chest.getInventory().firstEmpty() != -1) {
                                    ItemStack I = it[0].clone();
                                    ItemStack II = it[1].clone();
                                    I.setAmount(un[0].getAmount());
                                    II.setAmount(un[1].getAmount());
                                    player.getInventory().addItem(II);
                                    it[1].setAmount(it[1].getAmount()-un[1].getAmount());
                                    it[0].setAmount(it[0].getAmount()-un[0].getAmount());
                                    chest.getInventory().addItem(I);

                                }
                                else {player.sendMessage(ChatColor.DARK_RED+"Open an Inventory slot in the chest or buyer ");}
                            }
                        }

                    }
                }

            }

        }}



}
