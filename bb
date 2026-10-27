local Players = game:GetService("Players")
local UserInputService = game:GetService("UserInputService")
local RunService = game:GetService("RunService")
local Player = Players.LocalPlayer
local PlayerGui = Player:WaitForChild("PlayerGui")
local Old = PlayerGui:FindFirstChild("LoriBandzzBladeBall")
if Old then Old:Destroy() end
local Settings = {
	AutoParry = false,
	AutoSpam = false,
	AutoCurve = false,
	AbilityTracker = false,
	AntiAFK = false,
	ParryAccuracy = 75,
	ParryRange = 25,
	SpamRange = 12,
	SpamInterval = 0.12
}
-- Replace these adapters only with APIs provided by a game you own or are authorized to test.
local Adapter = {}
function Adapter.GetIncomingBall() return nil end
function Adapter.RequestParry() end
function Adapter.SetCurve(_enabled) end
function Adapter.GetAbilityStatus() return "Not connected" end
local Gui = Instance.new("ScreenGui")
Gui.Name = "LoriBandzzBladeBall"
Gui.ResetOnSpawn = false
Gui.ZIndexBehavior = Enum.ZIndexBehavior.Sibling
Gui.Parent = PlayerGui
local function New(class, props, parent)
	local obj = Instance.new(class)
	for key, value in pairs(props or {}) do obj[key] = value end
	obj.Parent = parent
	return obj
end
local function Corner(parent, radius)
	New("UICorner", {CornerRadius = UDim.new(0, radius or 10)}, parent)
end
local function Stroke(parent, color, thickness, transparency)
	New("UIStroke", {Color = color or Color3.fromRGB(164,165,175), Thickness = thickness or 1, Transparency = transparency or 0.35}, parent)
end
local BG = Color3.fromRGB(13,13,17)
local Panel = Color3.fromRGB(24,24,30)
local Text = Color3.fromRGB(245,240,247)
local Muted = Color3.fromRGB(158,155,166)
local Accent = Color3.fromRGB(230,168,202)
local Main = New("Frame", {Name="Main", Size=UDim2.fromOffset(520,390), Position=UDim2.new(0.5,-260,0.5,-195), BackgroundColor3=BG, BorderSizePixel=0}, Gui)
Corner(Main, 16)
Stroke(Main, Color3.fromRGB(194,195,207), 1.3, 0.2)
New("UIGradient", {Color=ColorSequence.new(Color3.fromRGB(38,38,47),BG), Rotation=90}, Main)
local Top = New("Frame", {Name="Top",Size=UDim2.new(1,0,0,62),BackgroundTransparency=1}, Main)
New("TextLabel", {Size=UDim2.new(1,-110,0,27),Position=UDim2.fromOffset(20,8),BackgroundTransparency=1,Text="LoriBandzz Private",TextColor3=Text,TextSize=21,Font=Enum.Font.GothamBold,TextXAlignment=Enum.TextXAlignment.Left}, Top)
New("TextLabel", {Size=UDim2.new(1,-110,0,19),Position=UDim2.fromOffset(21,36),BackgroundTransparency=1,Text="BLADE BALL  •  V1",TextColor3=Accent,TextSize=10,Font=Enum.Font.GothamBold,TextXAlignment=Enum.TextXAlignment.Left}, Top)
local Close = New("TextButton", {Size=UDim2.fromOffset(29,29),Position=UDim2.new(1,-42,0,15),BackgroundColor3=Panel,Text="×",TextSize=22,TextColor3=Text,Font=Enum.Font.Gotham}, Top)
Corner(Close,8)
Stroke(Close)
Close.MouseButton1Click:Connect(function() Gui:Destroy() end)
local Minimize = New("TextButton", {Size=UDim2.fromOffset(29,29),Position=UDim2.new(1,-78,0,15),BackgroundColor3=Panel,Text="−",TextSize=20,TextColor3=Text,Font=Enum.Font.Gotham}, Top)
Corner(Minimize,8)
Stroke(Minimize)
local Sidebar = New("Frame", {Position=UDim2.fromOffset(12,70),Size=UDim2.fromOffset(125,307),BackgroundColor3=Panel,BorderSizePixel=0}, Main)
Corner(Sidebar,10)
Stroke(Sidebar,nil,1,0.65)
New("UIPadding", {PaddingTop=UDim.new(0,10),PaddingLeft=UDim.new(0,8),PaddingRight=UDim.new(0,8)}, Sidebar)
New("UIListLayout", {Padding=UDim.new(0,7),SortOrder=Enum.SortOrder.LayoutOrder}, Sidebar)
local Content = New("ScrollingFrame", {Position=UDim2.fromOffset(148,70),Size=UDim2.new(1,-160,1,-83),BackgroundTransparency=1,ScrollBarThickness=3,ScrollBarImageColor3=Accent,CanvasSize=UDim2.fromOffset(0,0),AutomaticCanvasSize=Enum.AutomaticSize.Y}, Main)
New("UIListLayout", {Padding=UDim.new(0,9),SortOrder=Enum.SortOrder.LayoutOrder}, Content)
New("UIPadding", {PaddingBottom=UDim.new(0,10),PaddingRight=UDim.new(0,6)}, Content)
local Tabs = {}
local function Clear()
	for _, child in ipairs(Content:GetChildren()) do
		if child:IsA("GuiObject") then child:Destroy() end
	end
end
local function Heading(text)
	New("TextLabel", {Size=UDim2.new(1,0,0,27),BackgroundTransparency=1,Text=text,TextSize=17,Font=Enum.Font.GothamBold,TextColor=Text,TextXAlignment=Enum.TextXAlignment.Left},Content)
end
local function Toggle(label, desc, key)
	local Row = New("Frame", {Size=UDim2.new(1,0,0,63),BackgroundColor3=Panel,BorderSizePixel=0},Content)
	Corner(Row,10)
	Stroke(Row,nil,1,0.7)
	New("TextLabel", {Position=UDim2.fromOffset(12,8),Size=UDim2.new(1,-85,0,23),BackgroundTransparency=1,Text=label,Font=Enum.Font.GothamBold,TextSize=13,TextColor=Text,TextXAlignment=Enum.TextXAlignment.Left},Row)
	New("TextLabel", {Position=UDim2.fromOffset(12,31),Size=UDim2.new(1,-25,0,19),BackgroundTransparency=1,Text=desc,Font=Enum.Font.Gotham,TextSize=10,TextColor=Muted,TextXAlignment=Enum.TextXAlignment.Left},Row)
	local Button = New("TextButton", {Position=UDim2.new(1,-65,0,11),Size=UDim2.fromOffset(52,28),Text="",BackgroundColor3=Color3.fromRGB(62,61,70),BorderSizePixel=0},Row)
	Corner(Button,15)
	local Knob = New("Frame", {Position=UDim2.fromOffset(4,4),Size=UDim2.fromOffset(20,20),BackgroundColor3=Text,BorderSizePixel=0},Button)
	Corner(Knob,10)
	local function Refresh()
		Button.BackgroundColor3=Settings[key] and Accent or Color3.fromRGB(62,61,70)
		Knob.Position=Settings[key] and UDim2.fromOffset(28,4) or UDim2.fromOffset(4,4)
	end
	Button.MouseButton1Click:Connect(function()
		Settings[key]=not Settings[key]
		if key=="AutoCurve" then Adapter.SetCurve(Settings[key]) end
		Refresh()
	end)
	Refresh()
end
local function Slider(label,key,min,max,step,unit)
	local Row=New("Frame",{Size=UDim2.new(1,0,0,72),BackgroundColor3=Panel,BorderSizePixel=0},Content)
	Corner(Row,10)
	Stroke(Row,nil,1,0.7)
	New("TextLabel",{Position=UDim2.fromOffset(12,8),Size=UDim2.new(1,-110,0,20),BackgroundTransparency=1,Text=label,TextSize=12,Font=Enum.Font.GothamBold,TextColor=Text,TextXAlignment=Enum.TextXAlignment.Left},Row)
	local Value=New("TextLabel",{Position=UDim2.new(1,-96,0,8),Size=UDim2.fromOffset(83,20),BackgroundTransparency=1,TextSize=12,Font=Enum.Font.GothamBold,TextColor=Accent,TextXAlignment=Enum.TextXAlignment.Right},Row)
	local Track=New("Frame",{Position=UDim2.fromOffset(13,47),Size=UDim2.new(1,-26,0,7),BackgroundColor3=Color3.fromRGB(65,64,73),BorderSizePixel=0},Row)
	Corner(Track,4)
	local Fill=New("Frame",{Size=UDim2.fromScale(0,1),BackgroundColor3=Accent,BorderSizePixel=0},Track)
	Corner(Fill,4)
	local Dragging=false
	local function Refresh()
		Fill.Size=UDim2.fromScale((Settings[key]-min)/(max-min),1)
		Value.Text=string.format(step<1 and "%.2f%s" or "%.0f%s",Settings[key],unit or "")
	end
	local function Update(x)
		local alpha=math.clamp((x-Track.AbsolutePosition.X)/math.max(Track.AbsoluteSize.X,1),0,1)
		Settings[key]=math.clamp(math.floor((min+alpha*(max-min))/step+0.5)*step,min,max)
		Refresh()
	end
	Track.InputBegan:Connect(function(input)
		if input.UserInputType==Enum.UserInputType.MouseButton1 or input.UserInputType==Enum.UserInputType.Touch then Dragging=true Update(input.Position.X) end
	end)
	local moveConnection=UserInputService.InputChanged:Connect(function(input)
		if Dragging and (input.UserInputType==Enum.UserInputType.MouseMovement or input.UserInputType==Enum.UserInputType.Touch) then Update(input.Position.X) end
	end)
	local endConnection=UserInputService.InputEnded:Connect(function(input)
		if input.UserInputType==Enum.UserInputType.MouseButton1 or input.UserInputType==Enum.UserInputType.Touch then Dragging=false end
	end)
	Row.Destroying:Connect(function() moveConnection:Disconnect() endConnection:Disconnect() end)
	Refresh()
end
local function Info(label, desc)
	local Row=New("Frame",{Size=UDim2.new(1,0,0,61),BackgroundColor3=Panel,BorderSizePixel=0},Content)
	Corner(Row,10)
	Stroke(Row,nil,1,0.7)
	New("TextLabel",{Position=UDim2.fromOffset(12,8),Size=UDim2.new(1,-24,0,22),BackgroundTransparency=1,Text=label,TextSize=13,Font=Enum.Font.GothamBold,TextColor=Text,TextXAlignment=Enum.TextXAlignment.Left},Row)
	New("TextLabel",{Position=UDim2.fromOffset(12,31),Size=UDim2.new(1,-24,0,20),BackgroundTransparency=1,Text=desc,TextSize=10,Font=Enum.Font.Gotham,TextColor=Muted,TextXAlignment=Enum.TextXAlignment.Left},Row)
	return Row
end
local function Show(tab)
	Clear()
	for name,button in pairs(Tabs) do
		button.BackgroundColor3=name==tab and Color3.fromRGB(64,48,65) or Color3.fromRGB(30,30,37)
		button.TextColor3=name==tab and Accent or Text
	end
	if tab=="Combat" then
		Heading("Combat")
		Toggle("Auto Parry","Parry incoming balls","AutoParry")
		Toggle("Auto Spam","Close-range parry requests","AutoSpam")
		Toggle("Auto Curve","Apply configured curve behavior","AutoCurve")
		Slider("Parry Accuracy","ParryAccuracy",0,100,1,"%")
	elseif tab=="Utilities" then
		Heading("Utilities")
		Toggle("Ability Tracker","Show ability availability","AbilityTracker")
		Toggle("Anti-AFK","Requires an authorized idle adapter","AntiAFK")
		Info("Ability Status",Adapter.GetAbilityStatus())
	else
		Heading("Settings")
		Slider("Parry Distance","ParryRange",5,60,1," studs")
		Slider("Spam Distance","SpamRange",3,30,1," studs")
		Slider("Spam Interval","SpamInterval",0.05,0.5,0.01,"s")
		Info("Integration","Gameplay adapters are not connected.")
	end
end
for i,name in ipairs({"Combat","Utilities","Settings"}) do
	local Button=New("TextButton",{Name=name,LayoutOrder=i,Size=UDim2.new(1,0,0,39),BackgroundColor3=Color3.fromRGB(30,30,37),Text=name,TextSize=12,Font=Enum.Font.GothamBold,TextColor=Text},Sidebar)
	Corner(Button,8)
	Tabs[name]=Button
	Button.MouseButton1Click:Connect(function() Show(name) end)
end
Show("Combat")
local Minimized=false
Minimize.MouseButton1Click:Connect(function()
	Minimized=not Minimized
	Sidebar.Visible=not Minimized
	Content.Visible=not Minimized
	Main.Size=Minimized and UDim2.fromOffset(520,62) or UDim2.fromOffset(520,390)
end)
local Dragging,DragStart,StartPosition
Top.InputBegan:Connect(function(input)
	if input.UserInputType==Enum.UserInputType.MouseButton1 or input.UserInputType==Enum.UserInputType.Touch then
		Dragging=true
		DragStart=input.Position
		StartPosition=Main.Position
	end
end)
local dragMove=UserInputService.InputChanged:Connect(function(input)
	if Dragging and (input.UserInputType==Enum.UserInputType.MouseMovement or input.UserInputType==Enum.UserInputType.Touch) then
		local delta=input.Position-DragStart
		Main.Position=UDim2.new(StartPosition.X.Scale,StartPosition.X.Offset+delta.X,StartPosition.Y.Scale,StartPosition.Y.Offset+delta.Y)
	end
end)
local dragEnd=UserInputService.InputEnded:Connect(function(input)
	if input.UserInputType==Enum.UserInputType.MouseButton1 or input.UserInputType==Enum.UserInputType.Touch then Dragging=false end
end)
local LastParry=0
local loop=RunService.Heartbeat:Connect(function()
	if not (Settings.AutoParry or Settings.AutoSpam) then return end
	local ball,distance,approaching=Adapter.GetIncomingBall()
	if not ball or not approaching or type(distance)~="number" then return end
	local interval
	if Settings.AutoSpam and distance<=Settings.SpamRange then
		interval=Settings.SpamInterval
	elseif Settings.AutoParry and distance<=Settings.ParryRange then
		interval=math.max(0.08,0.25-Settings.ParryAccuracy*0.0015)
	end
	if interval and os.clock()-LastParry>=interval then
		LastParry=os.clock()
		Adapter.RequestParry()
	end
end)
Gui.Destroying:Connect(function()
	loop:Disconnect()
	dragMove:Disconnect()
	dragEnd:Disconnect()
end)
